/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.DepartDBContext;
import dal.PlanHeaderDBContext;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import model.Department;
import model.Product;
import model.ProductionPlan;
import model.ProductionPlanHeader;
import model.User;

/**
 *
 * @author FPTSHOP
 */
public class ProductionPlanUpdateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        PlanHeaderDBContext pdb = new PlanHeaderDBContext();
        DepartDBContext dbDept = new DepartDBContext();
        ProductDBContext dbProduct = new ProductDBContext();

        String plid_raw = req.getParameter("plid");
        int plid = -1;
        if (plid_raw != null) {
            plid = Integer.parseInt(plid_raw);
        }

        req.setAttribute("plans", pdb.listHeaders(plid).get(0));
        req.setAttribute("depts", dbDept.get("Production"));
        req.setAttribute("products", dbProduct.list());
        req.getRequestDispatcher("../view/productionPlan/update.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        ProductionPlan plan = new ProductionPlan();
        PlanHeaderDBContext phdb = new PlanHeaderDBContext();
        plan.setId(Integer.parseInt(req.getParameter("plid")));
        int plid = plan.getId();
        DepartDBContext dbDept = new DepartDBContext();
        ProductDBContext dbProduct = new ProductDBContext();

        String plname_raw = req.getParameter("name");
        String start_raw = req.getParameter("from");
        String end_raw = req.getParameter("to");
        String did_raw = req.getParameter("did");

        req.setAttribute("depts", dbDept.get("Production"));
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("plans", plan);

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

        if (hasError) {
            req.setAttribute("plans", phdb.listHeaders(plid).get(0));
            req.getRequestDispatcher("../view/productionPlan/update.jsp").forward(req, resp);
            return;
        }

// Thiết lập các thuộc tính và cập nhật kế hoạch sản xuất
        Department d = new Department();
        d.setId(Integer.parseInt(did_raw));
        plan.setDept(d);

        ArrayList<ProductionPlanHeader> headersToUpdate = new ArrayList<>();
        ArrayList<ProductionPlanHeader> headersToDelete = new ArrayList<>();
        String[] pids = req.getParameterValues("pid");

// Phân loại headers vào các list update hoặc delete
        for (String pid : pids) {
            Product p = new Product();
            p.setPid(Integer.parseInt(pid));

            String raw_quantity = req.getParameter("quantity" + pid);
            String raw_effort = req.getParameter("effort" + pid);

            int quantity = (raw_quantity != null && !raw_quantity.isEmpty()) ? Integer.parseInt(raw_quantity) : 0;
            float effort = (raw_effort != null && !raw_effort.isEmpty()) ? Float.parseFloat(raw_effort) : 0.0f;

            ProductionPlanHeader header = new ProductionPlanHeader();
            header.setProduct(p);
            header.setPlan(plan);
            header.setQuantity(quantity);
            header.setEstimatedeffort(effort);

            if (quantity > 0 && effort > 0) {
                headersToUpdate.add(header);
            } else {
                headersToDelete.add(header);
            }
        }

        ProductionPlanDBContext db = new ProductionPlanDBContext();

        db.updatePlan(plan);

        if (!headersToDelete.isEmpty()) {
            db.deleteHeaders(headersToDelete);
        }

        if (!headersToUpdate.isEmpty()) {
            db.updateOrInsertHeaders(headersToUpdate);
        }

        resp.sendRedirect("list");

    }

    public boolean checkDigit(String n) {
        for (int i = 0; i < n.length(); i++) {
            if (!Character.isDigit(n.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isNumber(String str) {
        return Pattern.matches("-?\\d+(\\.\\d+)?", str);
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
