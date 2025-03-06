/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.EmployeeDBContext;
import dal.ProductionPlanDBContext;
import dal.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import model.Employee;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.User;
import model.WorkAssignment;

/**
 *
 * @author FPTSHOP
 */
public class ProductionPlanAssignmentController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        // Lấy Plan ID từ tham số request
        int planId = Integer.parseInt(req.getParameter("planId"));

        // Sử dụng DAO để lấy chi tiết của kế hoạch sản xuất cùng thông tin công nhân
        ProductionPlanDBContext planDAO = new ProductionPlanDBContext();
        EmployeeDBContext edb = new EmployeeDBContext();
        ProductionPlan plan = planDAO.getEmployee(planId);

        int did = plan.getDept().getId();

        // Lấy danh sách các ngày từ kế hoạch
        ArrayList<String> dateList = generateDateRange(plan.getStart(), plan.getEnd());

        //String did = (String) req.getSession().getAttribute("deptId");
        // Đưa danh sách ngày và kế hoạch vào request scope
        req.setAttribute("plan", plan);
        req.setAttribute("dateList", dateList);
        req.setAttribute("emps", edb.listAtDepart(did));
        // Chuyển tiếp đến trang JSP để hiển thị thông tin chi tiết của công nhân
        req.getRequestDispatcher("../view/productionPlan/assignment.jsp").forward(req, resp);
        req.getSession().removeAttribute("errQuantitySchedule");
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        req.getSession().removeAttribute("errQuantitySchedule");
        int plid = Integer.parseInt(req.getParameter("planId"));
        PrintWriter out = resp.getWriter();
        ArrayList<WorkAssignment> works = new ArrayList<>();
        ArrayList<WorkAssignment> workDelete = new ArrayList<>();
        Enumeration<String> parameterNames = req.getParameterNames();

        boolean hasError = false;

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            if (paramName.startsWith("emp_")) {
                String employeeId = req.getParameter(paramName);
                String[] parts = paramName.split("_");
                String planDetailId = parts[1];
                String productId = parts[2];
                String shift = parts[3];
                String workAssignmentId = parts[4];

                // Truy xuất số lượng sản phẩm
                String quantityParam = "quantity_" + planDetailId + "_" + productId + "_" + shift + "_" + workAssignmentId;
                String quantityValue = req.getParameter(quantityParam);

                // Kiểm tra hợp lệ của quantity
                if (!isDigit(quantityValue)) {
                    req.getSession().setAttribute("errQuantitySchedule", "You must enter a valid quantity!");
                    resp.sendRedirect(req.getContextPath() + "/productionPlan/assignment?planId=" + plid);
                    return;
                }

                // Nếu hợp lệ, thêm vào danh sách công việc
                Employee e = new Employee();
                e.setId(Integer.parseInt(employeeId));

                ProductionPlanDetail pd = new ProductionPlanDetail();
                pd.setPdid(Integer.parseInt(planDetailId));

                WorkAssignment w = new WorkAssignment();
                w.setId(Integer.parseInt(workAssignmentId));
                w.setDetails(pd);
                w.setEmp(e);
                w.setQuantity(Integer.parseInt(quantityValue));

                if (w.getQuantity() > 0) {
                    works.add(w);
                } else {
                    workDelete.add(w);
                }
            }
        }

        if (works.size() > 0) {
            ScheduleDAO sd = new ScheduleDAO();
            sd.updateWorkAssignments(works);
        }

        if (workDelete.size() > 0) {
            ScheduleDAO sd = new ScheduleDAO();
            sd.deleteList(workDelete);
        }

        resp.sendRedirect("assignment?planId=" + plid);
    }
    
// Phương thức tạo danh sách các ngày từ ngày bắt đầu đến ngày kết thúc của kế hoạch

    private ArrayList<String> generateDateRange(java.sql.Date start, java.sql.Date end) {
        ArrayList<String> dateList = new ArrayList<>();
        java.util.Calendar startCal = java.util.Calendar.getInstance();
        startCal.setTime(start);
        java.util.Calendar endCal = java.util.Calendar.getInstance();
        endCal.setTime(end);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        // Lặp qua các ngày từ ngày bắt đầu đến ngày kết thúc
        while (!startCal.after(endCal)) {
            dateList.add(sdf.format(startCal.getTime()));
            startCal.add(java.util.Calendar.DATE, 1);
        }
        return dateList;
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
