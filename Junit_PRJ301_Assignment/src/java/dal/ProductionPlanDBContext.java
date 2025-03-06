/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dal.DBContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProductionPlan;
import java.sql.*;
import model.Department;
import model.Employee;
import model.Product;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.Shift;
import model.WorkAssignment;

/**
 *
 * @author sonnt-local
 */
public class ProductionPlanDBContext extends DBContext<ProductionPlan> {

    public void deletePlan(ProductionPlan model) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM Plans WHERE plid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            stm.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, "Error deleting plan", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
    }

    @Override
    public void insert(ProductionPlan model) {
        try {
            connection.setAutoCommit(false);
            String sql_insert_plan = "INSERT INTO [Plans]\n"
                    + "           ([plname]\n"
                    + "           ,[startdate]\n"
                    + "           ,[enddate]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";

            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setString(1, model.getName());
            stm_insert_plan.setDate(2, model.getStart());
            stm_insert_plan.setDate(3, model.getEnd());
            stm_insert_plan.setInt(4, model.getDept().getId());
            stm_insert_plan.executeUpdate();

            String sql_select_plan = "SELECT @@IDENTITY as plid";
            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("plid"));
            }

            String sql_insert_header = "INSERT INTO [PlanHeaders]\n"
                    + "           ([plid]\n"
                    + "           ,[pid]\n"
                    + "           ,[quantity]\n"
                    + "           ,[estimatedeffort])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";

            for (ProductionPlanHeader header : model.getHeaders()) {
                PreparedStatement stm_insert_header = connection.prepareStatement(sql_insert_header);
                stm_insert_header.setInt(1, model.getId());
                stm_insert_header.setInt(2, header.getProduct().getPid());
                stm_insert_header.setInt(3, header.getQuantity());
                stm_insert_header.setFloat(4, header.getEstimatedeffort());
                stm_insert_header.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } 

    }

    @Override
    public void update(ProductionPlan model) {
        try {
            connection.setAutoCommit(false);

            // Cập nhật thông tin của kế hoạch sản xuất
            String sql_update_plan = "UPDATE [Plans] SET [plname] = ?, [startdate] = ?, [enddate] = ?, [did] = ? WHERE plid = ?";
            PreparedStatement stm_update_plan = connection.prepareStatement(sql_update_plan);
            stm_update_plan.setString(1, model.getName());
            stm_update_plan.setDate(2, model.getStart());
            stm_update_plan.setDate(3, model.getEnd());
            stm_update_plan.setInt(4, model.getDept().getId());
            stm_update_plan.setInt(5, model.getId());
            stm_update_plan.executeUpdate();

            // Câu truy vấn kiểm tra xem header đã tồn tại hay chưa
            String sql_check_header_exists = "SELECT COUNT(*) FROM [PlanHeaders] WHERE plid = ? AND pid = ?";

            // Câu lệnh update header
            String sql_update_header = "UPDATE [PlanHeaders] SET [quantity] = ?, [estimatedeffort] = ? WHERE plid = ? AND pid = ?";

            // Câu lệnh insert header
            String sql_insert_header = "INSERT INTO [PlanHeaders] ([plid], [pid], [quantity], [estimatedeffort]) VALUES (?, ?, ?, ?)";

            for (ProductionPlanHeader header : model.getHeaders()) {
                PreparedStatement stm_check_header = connection.prepareStatement(sql_check_header_exists);
                stm_check_header.setInt(1, model.getId());
                stm_check_header.setInt(2, header.getProduct().getPid());
                ResultSet rs = stm_check_header.executeQuery();

                // Kiểm tra nếu header đã tồn tại (COUNT > 0), thực hiện UPDATE
                if (rs.next() && rs.getInt(1) > 0) {
                    PreparedStatement stm_update_header = connection.prepareStatement(sql_update_header);
                    stm_update_header.setInt(1, header.getQuantity());
                    stm_update_header.setFloat(2, header.getEstimatedeffort());
                    stm_update_header.setInt(3, model.getId());
                    stm_update_header.setInt(4, header.getProduct().getPid());
                    stm_update_header.executeUpdate();
                } else {
                    // Nếu chưa tồn tại, thực hiện INSERT
                    PreparedStatement stm_insert_header = connection.prepareStatement(sql_insert_header);
                    stm_insert_header.setInt(1, model.getId());
                    stm_insert_header.setInt(2, header.getProduct().getPid());
                    stm_insert_header.setInt(3, header.getQuantity());
                    stm_insert_header.setFloat(4, header.getEstimatedeffort());
                    stm_insert_header.executeUpdate();
                }
            }

            connection.commit(); // Xác nhận thay đổi
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback(); // Quay lại nếu có lỗi xảy ra
            } catch (SQLException ex1) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteDetails(ArrayList<ProductionPlanHeader> headers) {
        PreparedStatement stm = null;
        String sql = "DELETE FROM [PlanHeaders] \n"
                + "WHERE plid = ? \n"
                + "AND pid = ?";

        try {
            // Bắt đầu batch processing
            stm = connection.prepareStatement(sql);
            for (ProductionPlanHeader header : headers) {
                // Thiết lập các tham số cho lệnh xóa
                stm.setInt(1, header.getPlan().getId()); // Lấy plid từ plan liên kết với header
                stm.setInt(2, header.getProduct().getPid()); // Lấy pid từ sản phẩm liên kết với header

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
    public void delete(ProductionPlan model) {
        try {
            connection.setAutoCommit(false);

            String sql_delete_headers = "DELETE FROM [PlanHeaders] WHERE plid = ?";
            PreparedStatement stm_delete_headers = connection.prepareStatement(sql_delete_headers);
            stm_delete_headers.setInt(1, model.getId());
            stm_delete_headers.executeUpdate();

            String sql_delete_plan = "DELETE FROM [Plans] WHERE plid = ?";
            PreparedStatement stm_delete_plan = connection.prepareStatement(sql_delete_plan);
            stm_delete_plan.setInt(1, model.getId());
            stm_delete_plan.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<ProductionPlan> list() {
        String sql = "select pl.plid, pl.plname, pl.startdate, pl.enddate, d.did, d.dname, d.[type], p.pid, p.pname, p.[description], ph.phid, ph.quantity, ph.estimatedeffort from Plans pl\n"
                + "join Departments d\n"
                + "on pl.did = d.did\n"
                + "join PlanHeaders ph\n"
                + "on pl.plid = ph.plid\n"
                + "join Products p\n"
                + "on p.pid = ph.pid\n"
                + "order by pl.plid";

        ArrayList<ProductionPlan> plans = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
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

    @Override
    public ProductionPlan get(int id) {
        ProductionPlan plan = null;
        try {
            String sql = "SELECT p.plid, p.plname, p.startdate, p.enddate, d.did,\n"
                    + "ph.phid, prd.pname, ph.estimatedeffort, \n"
                    + "pd.date, pd.quantity, s.sname \n"
                    + "FROM Plans p \n"
                    + "LEFT JOIN Departments d ON p.did = d.did\n"
                    + "LEFT JOIN PlanHeaders ph ON p.plid = ph.plid \n"
                    + "LEFT JOIN PlanDetails pd ON ph.phid = pd.phid \n"
                    + "LEFT JOIN Products prd ON prd.pid = ph.pid \n"
                    + "LEFT JOIN Shifts s ON pd.sid = s.sid \n"
                    + "WHERE p.plid = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (plan == null) {
                    plan = new ProductionPlan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));
                    plan.setStart(rs.getDate("startdate"));
                    plan.setEnd(rs.getDate("enddate"));
                }
                // Tạo hoặc tìm PlanHeader
                ProductionPlanHeader header = findOrCreateHeader(plan, rs.getInt("phid"), rs.getInt("quantity"), rs.getFloat("estimatedeffort"));
                Product product = new Product();
                product.setPname(rs.getString("pname"));
                header.setProduct(product);

                // Tạo PlanDetail
                ProductionPlanDetail detail = new ProductionPlanDetail();
                detail.setDate(rs.getDate("date"));
                detail.setQuantity(rs.getInt("quantity"));

                Department d = new Department();
                d.setId(rs.getInt("did"));
                plan.setDept(d);

                // Tạo Shift
                Shift shift = new Shift();
                shift.setSname(rs.getString("sname"));
                detail.setShift(shift);

                header.getDetails().add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plan;
    }

    private ProductionPlanHeader findOrCreateHeader(ProductionPlan plan, int headerId, int quantity, float estimatedEffort) {
        for (ProductionPlanHeader header : plan.getHeaders()) {
            if (header.getId() == headerId) {
                return header;
            }
        }
        ProductionPlanHeader newHeader = new ProductionPlanHeader();
        newHeader.setId(headerId);
        newHeader.setQuantity(quantity);
        newHeader.setEstimatedeffort(estimatedEffort);
        plan.getHeaders().add(newHeader);
        return newHeader;
    }

    public ProductionPlan getEmployee(int id) {
        ProductionPlan plan = null;
        try {
            String sql = "SELECT p.plid, p.plname, p.startdate, p.enddate, d.did,\n"
                    + "ph.phid, prd.pid, prd.pname, ph.estimatedeffort, \n"
                    + "pd.pdid, pd.date, pd.quantity, s.sname, \n"
                    + "wa.waid, e.eid, e.ename, wa.quantity as work_quantity \n"
                    + "FROM Plans p \n"
                    + "LEFT JOIN PlanHeaders ph ON p.plid = ph.plid \n"
                    + "LEFT JOIN PlanDetails pd ON ph.phid = pd.phid \n"
                    + "LEFT JOIN Products prd ON prd.pid = ph.pid \n"
                    + "LEFT JOIN Shifts s ON pd.sid = s.sid \n"
                    + "LEFT JOIN WorkAssignments wa ON pd.pdid = wa.pdid\n"
                    + "LEFT JOIN Employees e ON wa.eid = e.eid \n"
                    + "LEFT JOIN Departments d ON p.did = d.did\n"
                    + "WHERE p.plid = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (plan == null) {
                    plan = new ProductionPlan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));
                    plan.setStart(rs.getDate("startdate"));
                    plan.setEnd(rs.getDate("enddate"));

                    Department d = new Department();
                    d.setId(rs.getInt("did"));

                    plan.setDept(d);
                }

                // Tìm hoặc tạo PlanHeader
                ProductionPlanHeader header = findOrCreateHeaderNew(plan, rs.getInt("phid"), rs.getInt("quantity"), rs.getFloat("estimatedeffort"));
                Product product = new Product();
                product.setPname(rs.getString("pname"));
                product.setPid(rs.getInt("pid"));
                header.setProduct(product);

                // Tạo hoặc tìm PlanDetail
                ProductionPlanDetail detail = findOrCreateDetailNew(header, rs.getDate("date"), rs.getInt("quantity"), rs.getString("sname"));
                detail.setPdid(rs.getInt("pdid"));
                // Tạo WorkAssignment
                if (rs.getInt("waid") != 0) {
                    WorkAssignment workAssignment = new WorkAssignment();
                    workAssignment.setId(rs.getInt("waid"));
                    workAssignment.setQuantity(rs.getInt("work_quantity"));

                    // Tạo Employee
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("eid"));
                    emp.setName(rs.getString("ename"));
                    workAssignment.setEmp(emp);

                    // Thêm WorkAssignment vào PlanDetail
                    detail.getWorks().add(workAssignment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plan;
    }

    private ProductionPlanHeader findOrCreateHeaderNew(ProductionPlan plan, int headerId, int quantity, float estimatedEffort) {
        for (ProductionPlanHeader header : plan.getHeaders()) {
            if (header.getId() == headerId) {
                return header;
            }
        }
        ProductionPlanHeader newHeader = new ProductionPlanHeader();
        newHeader.setId(headerId);
        newHeader.setQuantity(quantity);
        newHeader.setEstimatedeffort(estimatedEffort);
        plan.getHeaders().add(newHeader);
        return newHeader;
    }

    private ProductionPlanDetail findOrCreateDetailNew(ProductionPlanHeader header, Date date, int quantity, String shiftName) {
        for (ProductionPlanDetail detail : header.getDetails()) {
            if (detail.getDate().equals(date) && detail.getShift().getSname().equals(shiftName)) {
                return detail;
            }
        }
        ProductionPlanDetail newDetail = new ProductionPlanDetail();
        newDetail.setDate(date);
        newDetail.setQuantity(quantity);

        Shift shift = new Shift();
        shift.setSname(shiftName);
        newDetail.setShift(shift);

        header.getDetails().add(newDetail);
        return newDetail;
    }

    public void updateHeaders(ArrayList<ProductionPlanHeader> headers) {
        try {
            connection.setAutoCommit(false);

            String sql_update_header = "UPDATE [PlanHeaders] SET [quantity] = ?, [estimatedeffort] = ? WHERE plid = ? AND pid = ?";
            PreparedStatement stm_update = connection.prepareStatement(sql_update_header);

            for (ProductionPlanHeader header : headers) {
                stm_update.setInt(1, header.getQuantity());
                stm_update.setFloat(2, header.getEstimatedeffort());
                stm_update.setInt(3, header.getPlan().getId());
                stm_update.setInt(4, header.getProduct().getPid());
                stm_update.addBatch();
            }

            stm_update.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertHeaders(ArrayList<ProductionPlanHeader> headers) {
        try {
            connection.setAutoCommit(false);

            String sql_insert_header = "INSERT INTO [PlanHeaders] ([plid], [pid], [quantity], [estimatedeffort]) VALUES (?, ?, ?, ?)";
            PreparedStatement stm_insert = connection.prepareStatement(sql_insert_header);

            for (ProductionPlanHeader header : headers) {
                stm_insert.setInt(1, header.getPlan().getId());
                stm_insert.setInt(2, header.getProduct().getPid());
                stm_insert.setInt(3, header.getQuantity());
                stm_insert.setFloat(4, header.getEstimatedeffort());
                stm_insert.addBatch();
            }

            stm_insert.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updatePlan(ProductionPlan model) {
        PreparedStatement stm_update_plan = null;
        try {
            connection.setAutoCommit(false);

            // Cập nhật thông tin của kế hoạch sản xuất (Plans)
            String sql_update_plan = "UPDATE [Plans] SET [plname] = ?, [enddate] = ?, [did] = ? WHERE [plid] = ?";
            stm_update_plan = connection.prepareStatement(sql_update_plan);
            stm_update_plan.setString(1, model.getName());
            stm_update_plan.setDate(2, model.getEnd());
            stm_update_plan.setInt(3, model.getDept().getId());
            stm_update_plan.setInt(4, model.getId());

            // Thực hiện cập nhật
            int rowsAffected = stm_update_plan.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Plan information updated successfully.");
            } else {
                System.out.println("No Plan found with the specified ID.");
            }

            connection.commit(); // Xác nhận thay đổi
        } catch (SQLException ex) {
            try {
                System.err.println("Error during updating Plan: " + ex.getMessage());
                connection.rollback(); // Quay lại nếu có lỗi xảy ra
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (stm_update_plan != null) {
                    stm_update_plan.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deleteHeaders(ArrayList<ProductionPlanHeader> headersToDelete) {
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);

            String sql = "DELETE FROM [PlanHeaders] WHERE plid = ? AND pid = ?";
            stm = connection.prepareStatement(sql);

            for (ProductionPlanHeader header : headersToDelete) {
                stm.setInt(1, header.getPlan().getId());
                stm.setInt(2, header.getProduct().getPid());
                stm.addBatch();
            }

            stm.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateOrInsertHeaders(ArrayList<ProductionPlanHeader> headers) {
        try {
            connection.setAutoCommit(false);

            String sql_check_exists = "SELECT COUNT(*) FROM [PlanHeaders] WHERE plid = ? AND pid = ?";
            String sql_update_header = "UPDATE [PlanHeaders] SET [quantity] = ?, [estimatedeffort] = ? WHERE plid = ? AND pid = ?";
            String sql_insert_header = "INSERT INTO [PlanHeaders] ([plid], [pid], [quantity], [estimatedeffort]) VALUES (?, ?, ?, ?)";

            PreparedStatement stm_check_exists = connection.prepareStatement(sql_check_exists);
            PreparedStatement stm_update = connection.prepareStatement(sql_update_header);
            PreparedStatement stm_insert = connection.prepareStatement(sql_insert_header);

            for (ProductionPlanHeader header : headers) {
                // Kiểm tra xem header đã tồn tại chưa
                stm_check_exists.setInt(1, header.getPlan().getId());
                stm_check_exists.setInt(2, header.getProduct().getPid());
                ResultSet rs = stm_check_exists.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // Thực hiện update nếu đã tồn tại
                    stm_update.setInt(1, header.getQuantity());
                    stm_update.setFloat(2, header.getEstimatedeffort());
                    stm_update.setInt(3, header.getPlan().getId());
                    stm_update.setInt(4, header.getProduct().getPid());
                    stm_update.addBatch();
                } else {
                    // Thực hiện insert nếu chưa tồn tại
                    stm_insert.setInt(1, header.getPlan().getId());
                    stm_insert.setInt(2, header.getProduct().getPid());
                    stm_insert.setInt(3, header.getQuantity());
                    stm_insert.setFloat(4, header.getEstimatedeffort());
                    stm_insert.addBatch();
                }
            }

            // Thực thi batch update và insert
            stm_update.executeBatch();
            stm_insert.executeBatch();

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<ProductionPlan> listPlanWithActual() {
        String sql = "SELECT \n"
                + "    pl.plid,\n"
                + "    pl.plname AS plname,\n"
                + "    pl.startdate,\n"
                + "    pl.enddate,\n"
                + "    d.did,\n"
                + "    d.dname,\n"
                + "    d.[type],\n"
                + "    p.pid,\n"
                + "    p.pname,\n"
                + "    p.[description],\n"
                + "    ph.phid,\n"
                + "    ph.quantity AS QuantityRequired,\n"
                + "    ph.estimatedeffort AS EstimatedEffort,\n"
                + "    COALESCE(SUM(a.actualquantity), 0) AS TotalActualQuantity\n"
                + "FROM Plans pl\n"
                + "JOIN Departments d ON d.did = pl.did\n"
                + "JOIN PlanHeaders ph ON ph.plid = pl.plid\n"
                + "JOIN Products p ON p.pid = ph.pid\n"
                + "LEFT JOIN PlanDetails pd ON pd.phid = ph.phid\n"
                + "LEFT JOIN WorkAssignments w ON w.pdid = pd.pdid\n"
                + "LEFT JOIN Attendances a ON a.waid = w.waid\n"
                + "GROUP BY \n"
                + "    pl.plid, \n"
                + "    pl.plname, \n"
                + "    pl.startdate, \n"
                + "    pl.enddate, \n"
                + "    d.did, \n"
                + "    d.dname, \n"
                + "    d.[type], \n"
                + "    p.pid, \n"
                + "    p.pname, \n"
                + "    p.[description], \n"
                + "    ph.phid, \n"
                + "    ph.quantity, \n"
                + "    ph.estimatedeffort\n"
                + "ORDER BY \n"
                + "    pl.plid, \n"
                + "    p.pname";

        ArrayList<ProductionPlan> plans = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            ProductionPlan currentPlan = new ProductionPlan();
            currentPlan.setId(-1); // Khởi tạo ID giả để so sánh

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
                ph.setQuantity(rs.getInt("QuantityRequired"));
                ph.setEstimatedeffort(rs.getFloat("EstimatedEffort"));
                ph.setActualQuantity(rs.getInt("TotalActualQuantity")); // Gán tổng số lượng thực tế đạt được

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
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
    }

}
