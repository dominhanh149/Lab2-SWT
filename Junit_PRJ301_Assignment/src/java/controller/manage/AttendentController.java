/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.AttendentDAO;
import dal.EmployeeDBContext;
import dal.ProductionPlanDBContext;
import dal.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Employee;
import model.User;
import model.WorkAssignment;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import model.AttEmployee;
import model.AttProduct;
import model.Attendent;
import model.ProductionPlan;

/**
 *
 * @author FPTSHOP
 */
public class AttendentController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        int planId = Integer.parseInt(req.getParameter("planId"));  // Giả sử chúng ta có planId từ URL

// Lấy thông tin kế hoạch
        ProductionPlanDBContext pdb = new ProductionPlanDBContext();
        ProductionPlan plan = pdb.get(planId);

// Tạo danh sách ngày trong khoảng từ start đến end của kế hoạch
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        start.setTime(plan.getStart());
        Calendar end = Calendar.getInstance();
        end.setTime(plan.getEnd());

        while (!start.after(end)) {
            java.util.Date currentDate = start.getTime();
            String formattedDate = sdf.format(currentDate);
            dateList.add(formattedDate);
            start.add(Calendar.DATE, 1);
        }

// Tạo danh sách allWorkAssignments
        EmployeeDBContext edb = new EmployeeDBContext();
        ArrayList<WorkAssignment> allWorkAssignments = edb.getAllWorkAssignments(planId);  // lấy danh sách công nhân và công việc họ làm theo ngày

        AttendentDAO atd = new AttendentDAO();
        ArrayList<Attendent> listAttendent = atd.getListAttendent(planId);

// Gửi danh sách dateList, kế hoạch, và allWorkAssignments đến JSP
        req.setAttribute("plan", plan);
        req.setAttribute("dateList", dateList);
        req.setAttribute("allWorkAssignments", allWorkAssignments);
        req.setAttribute("attendents", listAttendent);

        req.getRequestDispatcher("../view/productionPlan/attendent.jsp").forward(req, resp);
        req.getSession().removeAttribute("err");

    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        req.getSession().removeAttribute("err");
        ArrayList<Attendent> listAttendent = new ArrayList<>();

        // Iterate over parameters to gather Attendent details
        Enumeration<String> parameterNames = req.getParameterNames();

        String err = "";
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            if (paramName.startsWith("actualQuantity_")) {
                // Extract identifiers from parameter name
                String[] parts = paramName.split("_");
                String waid = parts[1];
                String assignedQuantityParam = "assignedQuantity_" + waid;

                // Get parameter values
                String actualQuantityStr = req.getParameter(paramName);
                String assignedQuantityStr = req.getParameter(assignedQuantityParam);

                if (!isDigit(actualQuantityStr)) {
                    err = "Quantity must be a positive integer number!";
                    break;
                }

                // Parse actual quantity with default value of 0 if not entered
                int actualQuantity = actualQuantityStr != null && !actualQuantityStr.isEmpty()
                        ? Integer.parseInt(actualQuantityStr)
                        : 0;

                // Parse assigned quantity with a default of 1 to avoid division by zero
                int assignedQuantity = assignedQuantityStr != null && !assignedQuantityStr.isEmpty()
                        ? Integer.parseInt(assignedQuantityStr)
                        : 1;

                // Calculate alpha value
                float alpha = actualQuantity / (float) assignedQuantity;

                // Create WorkAssignment object
                WorkAssignment workAssignment = new WorkAssignment();
                workAssignment.setId(Integer.parseInt(waid));

                // Create Attendent object
                Attendent attendent = new Attendent();
                attendent.setWork(workAssignment);
                attendent.setActualQuantity(actualQuantity);
                attendent.setAlpha(alpha);

                // Add to list
                listAttendent.add(attendent);
            }
        }

        if (listAttendent.size() > 0) {
            AttendentDAO atd = new AttendentDAO();
            atd.saveOrUpdateAttendances(listAttendent);
        }

        int planId = Integer.parseInt(req.getParameter("planId")); 
        req.getSession().setAttribute("err", err);
        resp.sendRedirect("attendent?planId=" + planId);
    }

    public boolean isDigit(String n) {
        for (int i = 0; i < n.length(); i++) {
            if (!Character.isDigit(n.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
