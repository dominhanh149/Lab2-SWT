/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.PlanDetailDBContext;
import dal.PlanHeaderDBContext;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.Shift;
import model.User;

/**
 *
 * @author FPTSHOP
 */
public class ProductionPlanDetailController extends BaseRBACController {

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
            out.println("<title>Servlet ProductionPlanDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductionPlanDetailController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        // Lấy planId từ tham số request
        int planId = Integer.parseInt(req.getParameter("plid"));

        ProductionPlanDBContext pdb = new ProductionPlanDBContext();
        ProductionPlan plan = pdb.get(planId);

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

        req.setAttribute("plan", plan);
        req.getSession().setAttribute("deptId", plan.getDept().getId());
        req.setAttribute("dateList", dateList);

        // Chuyển hướng sang trang JSP để hiển thị thông tin chi tiết
        req.getRequestDispatcher("../view/productionPlan/detail.jsp").forward(req, resp);
        req.getSession().removeAttribute("errQuantityDetail");
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        req.getSession().removeAttribute("errQuantityDetail");
        int plid = parseQuantity(req.getParameter("plid"));

        int planId = Integer.parseInt(req.getParameter("plid"));

        ProductionPlanDBContext pdb = new ProductionPlanDBContext();
        ProductionPlan plan = pdb.get(planId);

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

        req.setAttribute("plan", plan);
        req.setAttribute("dateList", dateList);

        PlanDetailDBContext pddb = new PlanDetailDBContext();
        ArrayList<ProductionPlanHeader> headers = pddb.getHeadersByPlanId(plid);

        String[] dateRange = req.getParameterValues("dateRange");
        if (dateRange == null || dateRange.length == 0) {
            resp.getWriter().println("Date range is missing.");
            return;
        }

        ArrayList<ProductionPlanDetail> details = new ArrayList<>();
        ArrayList<ProductionPlanDetail> detailsDelete = new ArrayList<>();

        Boolean errQuantity = false;
        ArrayList<String> listQuantity = new ArrayList<>();

        for (ProductionPlanHeader header : headers) {
            int productID = header.getProduct().getPid();
            String productName = header.getProduct().getPname();
            for (String d : dateRange) {
                Date date = Date.valueOf(d);
                for (int shift = 1; shift <= 3; shift++) {
                    String param = "shift" + String.valueOf(shift) + "_quantity_" + d + "_" + productName;
                    String quantityRaw = req.getParameter(param);

                    if (quantityRaw.length() > 0 && quantityRaw != null) {
                        listQuantity.add(quantityRaw);
                    }
                }
            }
        }

        for (String string : listQuantity) {
            if (!isDigit(string)) {
                errQuantity = true;
                break;
            }
        }

        if (errQuantity) {
            req.getSession().setAttribute("errQuantityDetail", "You must enter a valid quantity!");
            //req.getRequestDispatcher("../view/productionPlan/detail.jsp").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/productionPlan/detail?plid=" + plid);
            return;
        }

        for (ProductionPlanHeader header : headers) {
            int productID = header.getProduct().getPid();
            String productName = header.getProduct().getPname();
            for (String d : dateRange) {
                Date date = Date.valueOf(d);
                for (int shift = 1; shift <= 3; shift++) {
                    String param = "shift" + String.valueOf(shift) + "_quantity_" + d + "_" + productName;
                    String quantityRaw = req.getParameter(param);

                    int quantity = quantityRaw != null && !quantityRaw.isEmpty() ? Integer.parseInt(quantityRaw) : 0;

                    if (quantity > 0) {
                        Shift s = new Shift();
                        s.setSid(shift);

                        ProductionPlanDetail detail = new ProductionPlanDetail();
                        detail.setPheader(header);
                        detail.setShift(s);
                        detail.setDate(date);
                        detail.setQuantity(quantity);

                        details.add(detail);
                    } else {
                        Shift s = new Shift();
                        s.setSid(shift);

                        ProductionPlanDetail detail = new ProductionPlanDetail();
                        detail.setPheader(header);
                        detail.setShift(s);
                        detail.setDate(date);

                        detailsDelete.add(detail);
                    }
                }
            }
        }

        if (!details.isEmpty()) {
            //pddb.insert(details);
            pddb.insertOrUpdate(details);
        }

        if (!detailsDelete.isEmpty()) {
            pddb.deleteDetails(detailsDelete);
        }

        String notifi = "Update succesfully!";

        resp.sendRedirect("detail?plid=" + plid + "&notifi=" + notifi);
    }

    // Phương thức để xử lý số lượng sản phẩm nhập vào
    private int parseQuantity(String quantity) {
        if (quantity == null || quantity.isEmpty()) {
            return 0;  // Mặc định nếu không nhập giá trị nào
        }
        return Integer.parseInt(quantity);
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
