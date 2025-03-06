/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.Product;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import java.sql.Date;
import model.Shift;

/**
 *
 * @author FPTSHOP
 */
public class PlanDetailDBContext extends DBContext<ProductionPlanDetail> {

    @Override
    public void insert(ProductionPlanDetail model) {
        PreparedStatement stm = null;
        String sql = "INSERT INTO [PlanDetails]\n"
                + "           ([phid]\n"
                + "           ,[sid]\n"
                + "           ,[date]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getPdid());
            stm.setInt(2, model.getShift().getSid());
            stm.setDate(3, model.getDate());
            stm.setInt(4, model.getQuantity());
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void insert(ArrayList<ProductionPlanDetail> details) {
        String sql = "INSERT INTO PlanDetails (phid, sid, date, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (ProductionPlanDetail detail : details) {
                ps.setInt(1, detail.getPheader().getId());
                ps.setInt(2, detail.getShift().getSid());
                ps.setDate(3, detail.getDate());
                ps.setInt(4, detail.getQuantity());
                ps.addBatch();
            }
            ps.executeBatch(); // Execute batch for better performance when inserting multiple rows
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProductionPlanHeader> getHeadersByPlanId(int planId) {
        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();
        String sql = "SELECT ph.phid, ph.plid, ph.pid, ph.quantity, ph.estimatedeffort, p.pname\n"
                + "FROM PlanHeaders ph\n"
                + "JOIN Products p ON ph.pid = p.pid\n"
                + "WHERE ph.plid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, planId); // Set the planId to the query
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductionPlanHeader header = new ProductionPlanHeader();
                header.setId(rs.getInt("phid")); // Set phid
                // header.getPlan().setId(rs.getInt("plid")); // Set plid
                header.setQuantity(rs.getInt("quantity")); // Set quantity
                header.setEstimatedeffort(rs.getFloat("estimatedeffort")); // Set estimatedeffort

                // Set associated product
                Product product = new Product();
                product.setPid(rs.getInt("pid")); // Set product id (pid)
                product.setPname(rs.getString("pname")); // Set product name (pname)
                header.setProduct(product); // Link product to the header

                headers.add(header); // Add to headers list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return headers;
    }

    public void insertOrUpdate(ArrayList<ProductionPlanDetail> details) {
        String selectSql = "SELECT pdid FROM PlanDetails WHERE phid = ? AND sid = ? AND date = ?";
        String insertSql = "INSERT INTO PlanDetails (phid, sid, date, quantity) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE PlanDetails SET quantity = ? WHERE pdid = ?";

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            for (ProductionPlanDetail detail : details) {
                int pdid = -1;

                // Kiểm tra xem bản ghi có tồn tại không
                try (PreparedStatement selectPs = connection.prepareStatement(selectSql)) {
                    selectPs.setInt(1, detail.getPheader().getId());
                    selectPs.setInt(2, detail.getShift().getSid());
                    selectPs.setDate(3, detail.getDate());

                    ResultSet rs = selectPs.executeQuery();
                    if (rs.next()) {
                        pdid = rs.getInt("pdid"); // Lấy pdid nếu tồn tại
                    }
                }

                // Nếu có dữ liệu (quantity lớn hơn 0) và tồn tại pdid, thực hiện UPDATE
                if (pdid != -1 && detail.getQuantity() > 0) {
                    try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                        updatePs.setInt(1, detail.getQuantity());
                        updatePs.setInt(2, pdid);
                        updatePs.executeUpdate();
                    }
                } // Nếu không có bản ghi và quantity > 0, thực hiện INSERT mới
                else if (pdid == -1 && detail.getQuantity() > 0) {
                    try (PreparedStatement insertPs = connection.prepareStatement(insertSql)) {
                        insertPs.setInt(1, detail.getPheader().getId());
                        insertPs.setInt(2, detail.getShift().getSid());
                        insertPs.setDate(3, detail.getDate());
                        insertPs.setInt(4, detail.getQuantity());
                        insertPs.executeUpdate();
                    }
                }
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback nếu có lỗi xảy ra
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Quay lại trạng thái auto-commit
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    public void deleteDetails(ArrayList<ProductionPlanDetail> details) {
        PreparedStatement stm = null;
        String sql = "DELETE FROM [PlanDetails] \n"
                + "WHERE phid = ?\n"
                + "and [sid] = ? \n"
                + "and [date] = ? \n";

        try {
            // Bắt đầu batch processing
            stm = connection.prepareStatement(sql);
            for (ProductionPlanDetail detail : details) {
                // Thiết lập các tham số cho lệnh xóa
                stm.setInt(1, detail.getPheader().getId());
                stm.setInt(2, detail.getShift().getSid());
                stm.setDate(3, detail.getDate());

                stm.addBatch(); // Thêm lệnh vào batch
            }

            // Thực hiện batch
            int[] result = stm.executeBatch();

            // Kiểm tra kết quả
            System.out.println("Số bản ghi đã bị xóa: " + result.length);

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(ProductionPlanDetail model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(ProductionPlanDetail model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductionPlanDetail> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductionPlanDetail get(int id) {
        return null;
    }

    public ArrayList<ProductionPlanDetail> listDetailPlan(int plid) {
        PreparedStatement stm = null;
        ArrayList<ProductionPlanDetail> list = new ArrayList<>();
        try {
            String sql = "select pl.plid, pl.plname, ph.phid, ph.pid, pd.pdid, pd.[sid], pd.quantity from Plans pl\n"
                    + "join PlanHeaders ph\n"
                    + "on pl.plid = ph.plid\n"
                    + "join PlanDetails pd\n"
                    + "on ph.phid = pd.phid\n"
                    + "join WorkAssignments w\n"
                    + "on w.pdid = pd.pdid\n"
                    + "where pl.plid = ?\n"
                    + "order by pd.[date]";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ProductionPlan pl = new ProductionPlan();
                pl.setId(rs.getInt("plid"));
                pl.setName(rs.getString("plname"));

                Product p = new Product();
                p.setPid(rs.getInt("pid"));

                ProductionPlanHeader ph = new ProductionPlanHeader();
                ph.setId(rs.getInt("phid"));
                ph.setProduct(p);

                ph.setPlan(pl);

                Shift s = new Shift();
                s.setSid(rs.getInt("sid"));

                ProductionPlanDetail pd = new ProductionPlanDetail();
                pd.setPdid(rs.getInt("pdid"));
                pd.setQuantity(rs.getInt("quantity"));
                pd.setShift(s);
                pd.setPheader(ph);

                list.add(pd);
            }

        } catch (Exception e) {
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

}
