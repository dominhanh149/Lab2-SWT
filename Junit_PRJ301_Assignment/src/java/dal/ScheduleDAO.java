/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.WorkAssignment;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Product;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.Shift;

/**
 *
 * @author FPTSHOP
 */
public class ScheduleDAO extends DBContext<WorkAssignment> {

    @Override
    public void insert(WorkAssignment model) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO [WorkAssignments]\n"
                    + "           ([pdid]\n"
                    + "           ,[eid]\n"
                    + "           ,[quantity])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getDetails().getPdid());
            stm.setInt(2, model.getEmp().getId());
            stm.setInt(3, model.getQuantity());

            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    @Override
    public void update(WorkAssignment model) {
    }

    @Override
    public void delete(WorkAssignment model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<WorkAssignment> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public WorkAssignment get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteList(ArrayList<WorkAssignment> workAssignments) {
        PreparedStatement stm = null;
        boolean originalAutoCommit = true;

        try {
            // Lưu lại trạng thái autoCommit ban đầu
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            String sql = "DELETE FROM [WorkAssignments] WHERE waid = ? AND pdid = ? AND eid = ?";
            stm = connection.prepareStatement(sql);

            for (WorkAssignment work : workAssignments) {
                stm.setInt(1, work.getId()); // waid
                stm.setInt(2, work.getDetails().getPdid()); // pdid
                stm.setInt(3, work.getEmp().getId()); // eid
                stm.addBatch();
            }

            int[] results = stm.executeBatch();

            // Kiểm tra kết quả
            boolean allSuccessful = true;
            for (int result : results) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    allSuccessful = false;
                    break;
                }
            }

            if (allSuccessful) {
                connection.commit();
                System.out.println("Đã xóa thành công " + results.length + " bản ghi");
            } else {
                connection.rollback();
                System.out.println("Không thể xóa tất cả các bản ghi, đã rollback");
            }
        } catch (SQLException e) {
            try {
                // Rollback transaction nếu có lỗi
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }

                // Khôi phục trạng thái autoCommit ban đầu
                if (connection != null && !connection.isClosed()) {
                    connection.setAutoCommit(originalAutoCommit);
                }

                // KHÔNG đóng connection ở đây nếu nó được quản lý bên ngoài
                // connection.close(); 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateWorkAssignments(ArrayList<WorkAssignment> workAssignments) {
        String sql = "UPDATE [WorkAssignments] SET [pdid] = ?, [eid] = ?, [quantity] = ? WHERE waid = ?";
        try {
            connection.setAutoCommit(false);  // Bắt đầu transaction để đảm bảo tính toàn vẹn

            PreparedStatement stmt = connection.prepareStatement(sql);

            for (WorkAssignment workAssignment : workAssignments) {
                stmt.setInt(1, workAssignment.getDetails().getPdid()); // Lấy pdid từ PlanDetail của WorkAssignment
                stmt.setInt(2, workAssignment.getEmp().getId());       // Lấy eid từ Employee của WorkAssignment
                stmt.setInt(3, workAssignment.getQuantity());
                stmt.setInt(4, workAssignment.getId());                // Lấy waid của WorkAssignment để xác định record cần update
                stmt.addBatch(); // Thêm vào batch để tăng hiệu suất thực thi
            }

            stmt.executeBatch();  // Thực thi batch update
            connection.commit();  // Xác nhận thay đổi sau khi batch update thành công
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(); // Quay lại nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true); // Đặt lại trạng thái auto-commit
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public WorkAssignment getAssignmentById(int id){
        String sql = "select * from WorkAssignments where waid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {                
                WorkAssignment w = new WorkAssignment();
                w.setId(id);
                
                ProductionPlanDetail pd = new ProductionPlanDetail();
                pd.setPdid(rs.getInt("pdid"));
                
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                
                w.setEmp(e);
                w.setDetails(pd);
                w.setQuantity(rs.getInt("quantity"));
                
                return w;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Map<Date, ArrayList<WorkAssignment>> getWorkAssignmentsByDate(int plid) {
        Map<Date, ArrayList<WorkAssignment>> workAssignmentsByDate = new HashMap<>();
        PreparedStatement stm = null;
        String sql = "SELECT "
                + "    e.eid, e.ename, e.address, e.phonenumber, e.salary, "
                + "    pl.plid AS PlanID, "
                + "    p.pname AS ProductName, "
                + "    s.sname AS ShiftName, "
                + "    pd.date AS WorkDate, "
                + "    w.quantity AS QuantityAssigned, "
                + "    ph.estimatedeffort AS EstimatedEffort "
                + "FROM Employees e "
                + "LEFT JOIN WorkAssignments w ON e.eid = w.eid "
                + "LEFT JOIN PlanDetails pd ON pd.pdid = w.pdid "
                + "LEFT JOIN Shifts s ON s.sid = pd.sid "
                + "LEFT JOIN PlanHeaders ph ON ph.phid = pd.phid "
                + "LEFT JOIN Products p ON p.pid = ph.pid "
                + "LEFT JOIN Plans pl ON pl.plid = ph.plid "
                + "WHERE pl.plid = ? "
                + "ORDER BY WorkDate, e.eid, p.pname, s.sname";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Date workDate = rs.getDate("WorkDate");

                Employee employee = new Employee();
                employee.setId(rs.getInt("eid"));
                employee.setName(rs.getString("ename"));
                employee.setAddress(rs.getString("address"));
                employee.setPhoneNumber(rs.getString("phonenumber"));
                employee.setSalary(rs.getFloat("salary"));

                ProductionPlan plan = new ProductionPlan();
                plan.setId(rs.getInt("PlanID"));

                Product product = new Product();
                product.setPname(rs.getString("ProductName"));

                Shift shift = new Shift();
                shift.setSname(rs.getString("ShiftName"));

                WorkAssignment workAssignment = new WorkAssignment();
                workAssignment.setEmp(employee);
                workAssignment.setQuantity(rs.getInt("QuantityAssigned"));

                ProductionPlanDetail planDetail = new ProductionPlanDetail();
                planDetail.setDate(workDate);
                planDetail.setShift(shift);

                ProductionPlanHeader planHeader = new ProductionPlanHeader();
                planHeader.setEstimatedeffort(rs.getFloat("EstimatedEffort"));
                planHeader.setPlan(plan);
                planHeader.setProduct(product);
                planDetail.setPheader(planHeader);

                workAssignment.setDetails(planDetail);

                // Group work assignments by date
                workAssignmentsByDate
                        .computeIfAbsent(workDate, k -> new ArrayList<>())
                        .add(workAssignment);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return workAssignmentsByDate;
    }

}
