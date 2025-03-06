/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manage;

import dal.DepartDBContext;
import dal.PlanDetailDBContext;
import dal.PlanHeaderDBContext;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import model.Employee;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.WorkAssignment;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        PlanHeaderDBContext phdb = new PlanHeaderDBContext();

        for (ProductionPlanHeader productionPlanHeader : phdb.getAll(1)) {
            out.println(productionPlanHeader.getProduct().getPname());
            for (ProductionPlanDetail detail : productionPlanHeader.getDetails()) {
                out.print(detail.getShift().getSname() + " ");
                out.print(detail.getQuantity() + " ");
                out.println(detail.getDate());
            }
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Loop over the keys that were set in the JSP
        for (String shift : Arrays.asList("K1", "K2", "K3")) {
            // Duyệt qua từng PlanDetail ID và Employee ID
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("employee_")) {
                    String pdid = request.getParameter("pdid_" + shift + "_" + paramName);
                    String empId = request.getParameter(paramName);
                    out.println("PlanDetail ID: " + pdid + ", Employee ID: " + empId + "<br>");
                }
            }
        }
    }

    public boolean checkWork(ArrayList<WorkAssignment> works, WorkAssignment w) {
        for (WorkAssignment work : works) {
            if (work.getDetails().getPdid() == w.getDetails().getPdid()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
