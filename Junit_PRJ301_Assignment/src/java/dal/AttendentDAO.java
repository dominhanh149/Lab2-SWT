/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Attendent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AttEmployee;
import model.AttProduct;
import model.Employee;
import model.Product;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.WorkAssignment;

/**
 *
 * @author FPTSHOP
 */
public class AttendentDAO extends DBContext<Attendent> {

    @Override
    public void insert(Attendent model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attendent model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attendent model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Attendent> list() {
        return null;
    }

    @Override
    public Attendent get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<AttEmployee> getWorkAssignmentsByEmployeeAndDate(int plid) {
        ArrayList<AttEmployee> workAssignments = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT "
                + "    e.eid, e.ename, e.salary, "
                + "    pl.plid AS PlanID, "
                + "    p.pid AS ProductID, "
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
                + "ORDER BY WorkDate,s.sname, p.pname, e.eid ";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int empId = rs.getInt("eid");
                String empName = rs.getString("ename");
                float empSalary = rs.getFloat("salary");
                Date workDate = rs.getDate("WorkDate");

                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String shiftName = rs.getString("ShiftName");
                int quantityAssigned = rs.getInt("QuantityAssigned");
                float estimatedEffort = rs.getFloat("EstimatedEffort");

                // Tạo AttProduct mới
                AttProduct product = new AttProduct();
                product.setId(productId);
                product.setName(productName);
                product.setShift(shiftName);
                product.setQuantityAssigned(quantityAssigned);
                product.setEstimatedeffort(estimatedEffort);

                // Tìm công nhân trong danh sách
                boolean employeeExists = false;
                for (AttEmployee existingEmployee : workAssignments) {
                    // Kiểm tra nếu công nhân đã có bản ghi cho ngày này
                    if (existingEmployee.getId() == empId && existingEmployee.getDate().equals(workDate)) {
                        existingEmployee.getPrds().add(product); // Thêm sản phẩm vào danh sách của công nhân
                        employeeExists = true;
                        break;
                    }
                }

                // Nếu công nhân chưa có bản ghi cho ngày này, tạo mới và thêm vào danh sách
                if (!employeeExists) {
                    AttEmployee newEmployee = new AttEmployee();
                    newEmployee.setId(empId);
                    newEmployee.setName(empName);
                    newEmployee.setSalary(empSalary);
                    newEmployee.setDate(workDate);

                    // Thêm sản phẩm đầu tiên
                    ArrayList<AttProduct> products = new ArrayList<>();
                    products.add(product);
                    newEmployee.setPrds(products);

                    workAssignments.add(newEmployee);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return workAssignments;
    }

    public void saveOrUpdateAttendances(ArrayList<Attendent> attendances) {
        PreparedStatement checkStm = null;
        PreparedStatement insertStm = null;
        PreparedStatement updateStm = null;

        String checkSql = "SELECT 1 FROM [Attendances] WHERE waid = ?";
        String insertSql = "INSERT INTO [Attendances] "
                + "([waid], [actualquantity], [alpha], [note]) "
                + "VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE [Attendances] "
                + "SET actualquantity = ?, alpha = ?, note = ? "
                + "WHERE waid = ?";

        try {
            // Chuẩn bị câu lệnh cho kiểm tra, chèn, và cập nhật
            checkStm = connection.prepareStatement(checkSql);
            insertStm = connection.prepareStatement(insertSql);
            updateStm = connection.prepareStatement(updateSql);

            // Bắt đầu transaction
            connection.setAutoCommit(false);

            for (Attendent att : attendances) {
                // Kiểm tra sự tồn tại của bản ghi với waid
                checkStm.setInt(1, att.getWork().getId());
                ResultSet rs = checkStm.executeQuery();

                if (rs.next()) {
                    // Nếu tồn tại, thực hiện cập nhật
                    updateStm.setInt(1, att.getActualQuantity());
                    updateStm.setFloat(2, att.getAlpha());
                    updateStm.setString(3, att.getNote());
                    updateStm.setInt(4, att.getWork().getId());
                    updateStm.addBatch();
                } else {
                    // Nếu không tồn tại, thực hiện chèn mới
                    insertStm.setInt(1, att.getWork().getId());
                    insertStm.setInt(2, att.getActualQuantity());
                    insertStm.setFloat(3, att.getAlpha());
                    insertStm.setString(4, att.getNote());
                    insertStm.addBatch();
                }
            }

            // Thực hiện batch insert và update
            insertStm.executeBatch();
            updateStm.executeBatch();

            // Xác nhận transaction
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                // Nếu lỗi, rollback lại transaction
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (checkStm != null) {
                    checkStm.close();
                }
                if (insertStm != null) {
                    insertStm.close();
                }
                if (updateStm != null) {
                    updateStm.close();
                }
                connection.setAutoCommit(true);  // Trả lại chế độ auto commit
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<Attendent> getListAttendent(int plid) {
        PreparedStatement stm = null;
        ArrayList<Attendent> list = new ArrayList<>();
        try {
            String sql = "SELECT \n"
                    + "	att.*\n"
                    + "FROM PlanHeaders ph\n"
                    + "JOIN Products p ON ph.pid = p.pid\n"
                    + "JOIN PlanDetails pd ON pd.phid = ph.phid\n"
                    + "JOIN Shifts s ON s.sid = pd.sid\n"
                    + "JOIN WorkAssignments w ON w.pdid = pd.pdid\n"
                    + "JOIN Employees e ON e.eid = w.eid\n"
                    + "JOIN Attendances att ON att.waid = w.waid\n"
                    + "WHERE ph.plid = ?\n"
                    + "ORDER BY pd.date, e.eid, s.sname;";
            
            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {            
                WorkAssignment w = new WorkAssignment();
                w.setId(rs.getInt("waid"));
                
                Attendent a = new Attendent();
                a.setId(rs.getInt("atid"));
                a.setActualQuantity(rs.getInt("actualquantity"));
                a.setAlpha(rs.getFloat("alpha"));
                a.setWork(w);
                
                list.add(a);
            }
        } catch (Exception e) {
        }
        finally{
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}
