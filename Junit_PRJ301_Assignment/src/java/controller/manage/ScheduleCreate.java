/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.EmployeeDBContext;
import dal.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import model.Employee;
import model.ProductionPlanDetail;
import model.User;
import model.WorkAssignment;
import java.sql.Date;

/**
 *
 * @author FPTSHOP
 */
public class ScheduleCreate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        EmployeeDBContext edb = new EmployeeDBContext();
        String pdid_raw = req.getParameter("pdid");
        String plid_raw = req.getParameter("plid");
        String did_raw = req.getParameter("did");
        Date date = Date.valueOf(req.getParameter("date"));
        int did = Integer.parseInt(did_raw);

        req.setAttribute("pdid", pdid_raw);
        req.setAttribute("plid", plid_raw);
        req.setAttribute("emps", edb.listAtDepartPlan(did, Integer.parseInt(plid_raw), Integer.parseInt(pdid_raw), date));
        req.getRequestDispatcher("../../view/productionPlan/assignmentcreate.jsp").forward(req, resp);
        req.getSession().removeAttribute("errQuantityScheduleCreate");
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        req.getSession().removeAttribute("errQuantityScheduleCreate");
        ScheduleDAO scd = new ScheduleDAO();
        String eid_raw = req.getParameter("eid");
        String quantity_raw = req.getParameter("quantity");
        String pdid_raw = req.getParameter("pdid");
        String plid_raw = req.getParameter("plid");
        String shift_raw = req.getParameter("shift");
        String date_raw = req.getParameter("date");
        String did_raw = req.getParameter("did");
        
        //?pdid=1&shift=K1&date=2024-10-01&plid=1

        if (!isDigit(quantity_raw)) {
            req.getSession().setAttribute("errQuantityScheduleCreate", "Quantity must be a positive integer!");
            resp.sendRedirect(req.getContextPath() + "/productionPlan/assignment/create?pdid="+pdid_raw+"&shift="+shift_raw+"&date="+date_raw+"&plid="+plid_raw+"&did="+did_raw);
            return;
        }

        ProductionPlanDetail pd = new ProductionPlanDetail();
        pd.setPdid(Integer.parseInt(pdid_raw));

        Employee e = new Employee();
        e.setId(Integer.parseInt(eid_raw));

        WorkAssignment w = new WorkAssignment();
        w.setDetails(pd);
        w.setEmp(e);
        w.setQuantity(Integer.parseInt(quantity_raw));

        scd.insert(w);
        resp.sendRedirect("../assignment?planId=" + plid_raw);
    }

    public boolean checkDigit(String n) {
        for (int i = 0; i < n.length(); i++) {
            if (!Character.isDigit(n.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isDigit(String n) {
        for (int i = 0; i < n.length(); i++) {
            if (!Character.isDigit(n.charAt(i))) {
                return false;
            }
        }
        if (Integer.parseInt(n) <= 0) {
            return false;
        }
        return true;
    }

}
