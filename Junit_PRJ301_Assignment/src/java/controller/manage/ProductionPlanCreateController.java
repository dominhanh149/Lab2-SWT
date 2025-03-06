/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.DepartDBContext;
import dal.ProductDBContext;
import dal.DepartDBContext;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductionPlan;
import java.sql.*;
import model.Department;
import model.Product;
import model.ProductionPlanHeader;
import model.User;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author sonnt-local hand-some
 */
public class ProductionPlanCreateController extends BaseRBACController {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        DepartDBContext dbDept = new DepartDBContext();
        ProductDBContext dbProduct = new ProductDBContext();

        req.setAttribute("depts", dbDept.get("Production"));
        req.setAttribute("products", dbProduct.list());

        req.getRequestDispatcher("../view/productionPlan/create.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        ProductionPlan plan = new ProductionPlan();
        DepartDBContext dbDept = new DepartDBContext();
        ProductDBContext dbProduct = new ProductDBContext();

        String plname_raw = req.getParameter("name");
        String start_raw = req.getParameter("from");
        String end_raw = req.getParameter("to");
        String did_raw = req.getParameter("did");

        req.setAttribute("depts", dbDept.get("Production"));
        req.setAttribute("products", dbProduct.list());

// Cờ kiểm tra lỗi
        boolean hasError = false;

// Kiểm tra Plan Name
        if (plname_raw == null || plname_raw.isBlank()) {
            req.setAttribute("errName", "Name must not be empty!");
            hasError = true;
        } else {
            plan.setName(plname_raw);
        }

// Kiểm tra Date From và Date To
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate startDate = LocalDate.parse(start_raw);
            LocalDate endDate = LocalDate.parse(end_raw);

            if (startDate.isBefore(currentDate)) {
                req.setAttribute("errFrom", "Date From must be today or a future date!");
                hasError = true;
            } else {
                plan.setStart(Date.valueOf(start_raw));
            }

            if (endDate.isBefore(startDate)) {
                req.setAttribute("errTo", "Date To must be after Date From!");
                hasError = true;
            } else {
                plan.setEnd(Date.valueOf(end_raw));
            }

        } catch (DateTimeParseException e) {
            req.setAttribute("errFrom", "Invalid date format!");
            req.setAttribute("errTo", "Invalid date format!");
            hasError = true;
        }

// Nếu có lỗi, chuyển về trang create.jsp
        if (hasError) {
            req.getRequestDispatcher("../view/productionPlan/create.jsp").forward(req, resp);
            return;
        }

        plan.setName(req.getParameter("name"));
        plan.setStart(Date.valueOf(req.getParameter("from")));
        plan.setEnd(Date.valueOf(req.getParameter("to")));

        Department d = new Department();
        d.setId(Integer.parseInt(req.getParameter("did")));

        plan.setDept(d);

        String[] pids = req.getParameterValues("pid");
        
        Boolean errProduct = false;
        
        for (String pid : pids) {
            String raw_quantity = req.getParameter("quantity" + pid);
            String raw_effort = req.getParameter("effort" + pid);
            
            if(raw_quantity == null && raw_quantity.length() <= 0 || !checkDigit(raw_quantity)){
                errProduct = true;
            }
            
            if(raw_effort == null && raw_effort.length() <= 0 || !checkDigit(raw_effort)){
                errProduct = true;
            }
        }
        
        if(errProduct){
            req.setAttribute("errProduct", "Please enter a valid quantity and effort!");
            req.getRequestDispatcher("../view/productionPlan/create.jsp").forward(req, resp);
            return;
        }
        
        for (String pid : pids) {
            Product p = new Product();
            p.setPid(Integer.parseInt(pid));

            ProductionPlanHeader header = new ProductionPlanHeader();
            header.setProduct(p);
            String raw_quantity = req.getParameter("quantity" + pid);
            String raw_effort = req.getParameter("effort" + pid);
            header.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            header.setEstimatedeffort(raw_effort != null && raw_effort.length() > 0 ? Float.parseFloat(raw_effort) : 0);
            

            if (header.getQuantity() > 0 && header.getEstimatedeffort() > 0) {
                plan.getHeaders().add(header);
            }
        }

        if (plan.getHeaders().size() > 0) {
            ProductionPlanDBContext db = new ProductionPlanDBContext();
            db.insert(plan);
            resp.sendRedirect("list");
        } else {
            //resp.getWriter().println("your plan does not have any headers! it is not allowed!");
            req.setAttribute("emptyHeader", "Your plan does not have any headers! It is not allowed!");
            req.getRequestDispatcher("../view/productionPlan/create.jsp").forward(req, resp);
        }

    }
    
    public boolean checkDigit(String n){
        for (int i = 0; i < n.length(); i++) {
            if(!Character.isDigit(n.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
