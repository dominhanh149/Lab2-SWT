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
import model.Employee;
import model.Product;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.Shift;
import model.WorkAssignment;
import java.sql.Date;
import model.Department;
/**
 *
 * @author FPTSHOP
 */
public class EmployeeDBContext extends DBContext<Employee> {

    @Override
    public void insert(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Employee> list() {
        PreparedStatement stm = null;
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            String sql = "select eid, ename, phonenumber, [address], salary from Employees";

            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                e.setAddress(rs.getString("address"));
                e.setPhoneNumber(rs.getString("phonenumber"));
                e.setSalary(rs.getFloat("salary"));

                emps.add(e);
            }
        } catch (Exception e) {
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emps;
    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Employee> listAtDepart(int did) {
        PreparedStatement stm = null;
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            String sql = "select e.eid, e.ename, e.[address], e.phonenumber, e.salary from Employees e\n"
                    + "left join Departments d\n"
                    + "on e.did = d.did\n"
                    + "left join Plans pl\n"
                    + "on pl.did = d.did\n"
                    + "left join PlanHeaders ph\n"
                    + "on ph.plid = pl.plid\n"
                    + "left join PlanDetails pd\n"
                    + "on pd.phid = ph.phid\n"
                    + "left join Shifts s\n"
                    + "on pd.sid = s.sid\n"
                    + "left join WorkAssignments w\n"
                    + "on w.pdid = pd.pdid\n"
                    + "where d.did = ?\n"
                    + "group by e.eid, e.ename, e.[address], e.phonenumber, e.salary";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, did);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                e.setAddress(rs.getString("address"));
                e.setPhoneNumber(rs.getString("phonenumber"));
                e.setSalary(rs.getFloat("salary"));

                emps.add(e);
            }
        } catch (Exception e) {
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emps;
    }

    public ArrayList<Employee> listAtDepartPlan(int did, int plid, int pdid, Date date) {
        PreparedStatement stm = null;
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            String sql = "SELECT e.eid, e.ename, COALESCE(COUNT(s.sid), 0) AS shift_count\n"
                    + "FROM Employees e\n"
                    + "LEFT JOIN WorkAssignments w ON e.eid = w.eid\n"
                    + "LEFT JOIN PlanDetails pd ON w.pdid = pd.pdid AND pd.date = ?\n"
                    + "LEFT JOIN Shifts s ON s.sid = pd.sid\n"
                    + "LEFT JOIN PlanHeaders ph ON pd.phid = ph.phid AND ph.plid = ?\n"
                    + "WHERE e.did = ?\n"
                    + "  AND NOT EXISTS (\n"
                    + "      SELECT 1 \n"
                    + "      FROM WorkAssignments wa \n"
                    + "      JOIN PlanDetails pd_sub ON wa.pdid = pd_sub.pdid\n"
                    + "      WHERE wa.eid = e.eid \n"
                    + "        AND wa.pdid = ? \n"
                    + "        AND pd_sub.date = ?\n"
                    + "  )\n"
                    + "GROUP BY e.eid, e.ename\n"
                    + "HAVING COUNT(s.sid) < 2\n"
                    + "ORDER BY e.eid;";

            stm = connection.prepareStatement(sql);
            stm.setDate(1, date);
            stm.setInt(2, plid);
            stm.setInt(3, did);
            stm.setInt(4, pdid);
            stm.setDate(5, date);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));

                emps.add(e);
            }
        } catch (Exception e) {
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emps;
    }

    public ArrayList<WorkAssignment> getAllWorkAssignments(int planId) {
        ArrayList<WorkAssignment> workAssignments = new ArrayList<>();
        String sql = "SELECT "
                + "    w.waid, e.eid, e.ename, e.salary, "
                + "    pd.date AS WorkDate, "
                + "    s.sname AS ShiftName, "
                + "    w.quantity AS QuantityAssigned, "
                + "    p.pname AS ProductName, "
                + "    ph.estimatedeffort AS EstimatedEffort "
                + "FROM Employees e "
                + "JOIN WorkAssignments w ON e.eid = w.eid "
                + "JOIN PlanDetails pd ON pd.pdid = w.pdid "
                + "JOIN Shifts s ON s.sid = pd.sid "
                + "JOIN PlanHeaders ph ON ph.phid = pd.phid "
                + "JOIN Products p ON p.pid = ph.pid "
                + "WHERE ph.plid = ? "
                + "ORDER BY pd.date, s.sname, e.eid ";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                // Tạo đối tượng Employee
                Employee employee = new Employee();
                employee.setId(rs.getInt("eid"));
                employee.setName(rs.getString("ename"));
                employee.setSalary(rs.getFloat("salary"));

                // Tạo đối tượng Product
                Product product = new Product();
                product.setPname(rs.getString("ProductName"));

                // Tạo đối tượng ProductionPlanHeader
                ProductionPlanHeader planHeader = new ProductionPlanHeader();
                planHeader.setEstimatedeffort(rs.getFloat("EstimatedEffort"));
                planHeader.setProduct(product);

                // Tạo đối tượng Shift
                Shift shift = new Shift();
                shift.setSname(rs.getString("ShiftName"));

                // Tạo đối tượng ProductionPlanDetail
                ProductionPlanDetail planDetail = new ProductionPlanDetail();
                planDetail.setDate(rs.getDate("WorkDate"));
                planDetail.setShift(shift);
                planDetail.setPheader(planHeader);

                // Tạo đối tượng WorkAssignment
                WorkAssignment workAssignment = new WorkAssignment();
                workAssignment.setId(rs.getInt("waid"));
                workAssignment.setEmp(employee);
                workAssignment.setDetails(planDetail);
                workAssignment.setQuantity(rs.getInt("QuantityAssigned"));

                // Thêm WorkAssignment vào danh sách
                workAssignments.add(workAssignment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return workAssignments;
    }
    
    public ArrayList<Employee> listJsp() {
        PreparedStatement stm = null;
        ArrayList<Employee> emps = new ArrayList<>();
        try {
            String sql = "select eid, ename, phonenumber, [address], salary, d.did, d.dname from Employees e "
                    + "join Departments d on d.did = e.did";

            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                e.setAddress(rs.getString("address"));
                e.setPhoneNumber(rs.getString("phonenumber"));
                e.setSalary(rs.getFloat("salary"));
                
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                e.setDept(d);

                emps.add(e);
            }
        } catch (Exception e) {
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emps;
    }

}
