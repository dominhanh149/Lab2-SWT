/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.DepartDBContext;
import dal.EmployeeWorkInforDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import model.Department;

/**
 *
 * @author FPTSHOP
 */
public class ViewSalary extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        EmployeeWorkInforDAO edb = new EmployeeWorkInforDAO();
        DepartDBContext ddb = new DepartDBContext();
        Integer did = null;
        Date from = getFirstDayOfMonth();
        Date to = getLastDayOfMonth();

        req.setAttribute("depts", ddb.get("Production"));
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        req.setAttribute("salaryEmps", edb.getEmployeeSalaryInMonth1(from, to, did));
        req.getRequestDispatcher("../view/productionPlan/viewsalary.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        EmployeeWorkInforDAO edb = new EmployeeWorkInforDAO();
        DepartDBContext ddb = new DepartDBContext();

        Date from = Date.valueOf(req.getParameter("from"));
        Date to = Date.valueOf(req.getParameter("to"));
        String did_raw = req.getParameter("did");
        Integer did = null;
        if(did_raw.equals("0")){
            did = null;
        }else{
            did = Integer.parseInt(did_raw);
        }

        req.setAttribute("did", did_raw);
        req.setAttribute("depts", ddb.get("Production"));
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        req.setAttribute("salaryEmps", edb.getEmployeeSalaryInMonth1(from, to, did));
        req.setAttribute("salary", edb.getEmployee());
        req.getRequestDispatcher("../view/productionPlan/viewsalary.jsp").forward(req, resp);
    }

    public static Date getFirstDayOfMonth() {
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        return Date.valueOf(firstDay);  // Chuyển đổi sang java.sql.Date
    }

    public static Date getLastDayOfMonth() {
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return Date.valueOf(lastDay);  // Chuyển đổi sang java.sql.Date
    }

}
