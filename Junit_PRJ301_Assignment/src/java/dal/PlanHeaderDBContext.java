/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.Shift;
import java.sql.Date;
import model.Department;

/**
 *
 * @author FPTSHOP
 */
public class PlanHeaderDBContext extends DBContext<ProductionPlanHeader> {

    @Override
    public void insert(ProductionPlanHeader model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(ProductionPlanHeader model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(ProductionPlanHeader model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductionPlanHeader> list() {

        return null;

    }

    @Override
    public ProductionPlanHeader get(int id) {

        return null;

    }

    public ArrayList<ProductionPlan> listHeaders(int plid) {
        String sql = "select pl.plid, pl.plname, pl.startdate, pl.enddate, d.did, d.dname, d.[type], p.pid, p.pname, p.[description], ph.phid, ph.quantity, ph.estimatedeffort from Plans pl\n"
                + "join Departments d\n"
                + "on pl.did = d.did\n"
                + "join PlanHeaders ph\n"
                + "on pl.plid = ph.plid\n"
                + "join Products p\n"
                + "on p.pid = ph.pid\n"
                + "where pl.plid = ?";

        ArrayList<ProductionPlan> plans = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);
            ResultSet rs = stm.executeQuery();

            ProductionPlan currentPlan = new ProductionPlan();
            currentPlan.setId(-1);
            while (rs.next()) {
                int currentPlid = rs.getInt("plid");
                if (currentPlid != currentPlan.getId()) {
                    currentPlan = new ProductionPlan();
                    currentPlan.setId(currentPlid);
                    currentPlan.setName(rs.getString("plname"));
                    currentPlan.setStart(rs.getDate("startdate"));
                    currentPlan.setEnd(rs.getDate("enddate"));

                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    d.setName(rs.getString("dname"));
                    d.setType(rs.getString("type"));

                    currentPlan.setDept(d);
                    currentPlan.setHeaders(new ArrayList<>());

                    plans.add(currentPlan);
                }

                ProductionPlanHeader ph = new ProductionPlanHeader();
                ph.setId(rs.getInt("phid"));
                ph.setQuantity(rs.getInt("quantity"));
                ph.setEstimatedeffort(rs.getFloat("estimatedeffort"));

                Product p = new Product();
                p.setPid(rs.getInt("pid"));
                p.setPname(rs.getString("pname"));
                p.setDescription(rs.getString("description"));

                ph.setProduct(p);
                ph.setPlan(currentPlan);

                currentPlan.getHeaders().add(ph);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
    }

    public ArrayList<ProductionPlanHeader> getAll(int plid) {
        String sql = "SELECT pl.plid, pl.plname, pl.startdate, pl.enddate, ph.phid, "
                + "p.pid, p.pname, s.sid, s.sname, pd.pdid, pd.date, pd.quantity as currentQuantity, "
                + "ph.quantity as quantityTotal, ph.estimatedeffort "
                + "FROM Plans pl "
                + "JOIN PlanHeaders ph ON pl.plid = ph.plid "
                + "JOIN PlanDetails pd ON pd.phid = ph.phid "
                + "JOIN Products p ON p.pid = ph.pid "
                + "JOIN Shifts s ON s.sid = pd.sid "
                + "WHERE pl.plid = ? "
                + "ORDER BY pd.date, s.sid";

        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, plid);
            ResultSet rs = stm.executeQuery();

            ProductionPlanHeader currentPlanHeader = null;
            Date lastDate = null;

            while (rs.next()) {
                Date currentDate = rs.getDate("date");

                // Nếu ngày thay đổi, tạo một ProductionPlanHeader mới
                if (currentPlanHeader == null || !currentDate.equals(lastDate)) {
                    currentPlanHeader = new ProductionPlanHeader();
                    currentPlanHeader.setId(rs.getInt("phid"));
                    currentPlanHeader.setEstimatedeffort(rs.getFloat("estimatedeffort"));
                    currentPlanHeader.setQuantity(rs.getInt("quantityTotal"));

                    ProductionPlan pl = new ProductionPlan();
                    pl.setId(rs.getInt("plid"));
                    pl.setName(rs.getString("plname"));
                    pl.setStart(rs.getDate("startdate"));
                    pl.setEnd(rs.getDate("enddate"));
                    currentPlanHeader.setPlan(pl);

                    Product p = new Product();
                    p.setPid(rs.getInt("pid"));
                    p.setPname(rs.getNString("pname"));
                    currentPlanHeader.setProduct(p);

                    currentPlanHeader.setDetails(new ArrayList<>());
                    headers.add(currentPlanHeader);
                }

                // Tạo một ProductionPlanDetail mới và thêm vào kế hoạch hiện tại
                ProductionPlanDetail pd = new ProductionPlanDetail();
                pd.setPdid(rs.getInt("pdid"));
                pd.setDate(currentDate);
                pd.setQuantity(rs.getInt("currentQuantity"));

                Shift s = new Shift();
                s.setSid(rs.getInt("sid"));
                s.setSname(rs.getString("sname"));
                pd.setShift(s);
                pd.setPheader(currentPlanHeader);

                currentPlanHeader.getDetails().add(pd);
                lastDate = currentDate; // Cập nhật ngày cuối cùng
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanHeaderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return headers;
    }

}
