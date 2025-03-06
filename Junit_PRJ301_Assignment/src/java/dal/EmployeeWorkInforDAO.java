/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.EmployeeWorkInfo;
import java.sql.*;

/**
 *
 * @author FPTSHOP
 */
public class EmployeeWorkInforDAO extends DBContext<EmployeeWorkInfo> {

    @Override
    public void insert(EmployeeWorkInfo model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(EmployeeWorkInfo model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(EmployeeWorkInfo model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<EmployeeWorkInfo> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public EmployeeWorkInfo get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<EmployeeWorkInfo> getEmployeeWorkInfoList(Date from, Date to, Integer did) {
        ArrayList<EmployeeWorkInfo> employeeWorkInfoList = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT\n"
                + "    e.eid,\n"
                + "    e.ename,\n"
                + "    e.salary,\n"
                + "    pd.date,\n"
                + "    p.pname AS ProductName,\n"
                + "    ph.estimatedeffort AS EstimatedEffort,\n"
                + "    COALESCE(SUM(w.quantity), 0) AS TotalQuantityAssignedPerDay,\n"
                + "    COALESCE(SUM(a.actualquantity), 0) AS TotalActualQuantity,\n"
                + "    COALESCE(AVG(a.alpha), 1) AS AverageAlpha,\n"
                + "    COUNT(s.sname) AS TotalShifts,\n"
                + "    (8 * COUNT(s.sname) * e.salary * COALESCE(ph.estimatedeffort, 0) * \n"
                + "    COALESCE(SUM(w.quantity), 0) * COALESCE(AVG(a.alpha), 1)) AS CalculatedSalary\n"
                + "FROM Employees e\n"
                + "LEFT JOIN WorkAssignments w ON e.eid = w.eid\n"
                + "LEFT JOIN PlanDetails pd ON w.pdid = pd.pdid AND ((pd.date BETWEEN ? AND ?) OR pd.date IS NULL) \n"
                + "LEFT JOIN Shifts s ON pd.sid = s.sid \n"
                + "LEFT JOIN PlanHeaders ph ON pd.phid = ph.phid\n"
                + "LEFT JOIN Products p ON ph.pid = p.pid\n"
                + "LEFT JOIN Attendances a ON w.waid = a.waid\n";

        if (did != null) {
            sql += "WHERE e.did = ?";
        }

        sql += "GROUP BY\n"
                + "    e.eid, e.ename, e.salary, pd.date, p.pname, ph.estimatedeffort\n"
                + "ORDER BY\n"
                + "    CASE WHEN pd.date IS NULL THEN 1 ELSE 0 END,\n"
                + "    CASE WHEN p.pname IS NULL THEN 1 ELSE 0 END,\n"
                + "    e.eid, pd.date, p.pname;";

        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, from);
            stm.setDate(2, to);
            if (did != null) {
                stm.setInt(3, did);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeWorkInfo info = new EmployeeWorkInfo();

                // Gán dữ liệu từ ResultSet vào EmployeeWorkInfo
                info.setEid(rs.getInt("eid"));
                info.setEname(rs.getString("ename"));
                info.setSalary(rs.getFloat("salary"));
                info.setDate(rs.getDate("date"));
                info.setProductName(rs.getString("ProductName"));
                info.setEstimatedEffort(rs.getFloat("EstimatedEffort"));
                info.setQuantityAssigned(rs.getInt("TotalQuantityAssignedPerDay"));
                info.setActualQuantity(rs.getInt("TotalActualQuantity"));
                info.setAlpha(rs.getFloat("AverageAlpha"));
                info.setTotalShifts(rs.getInt("TotalShifts"));
                info.setCalculatedSalary(rs.getFloat("CalculatedSalary"));

                // Thêm đối tượng EmployeeWorkInfo vào danh sách
                employeeWorkInfoList.add(info);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeeWorkInfoList;
    }

    public ArrayList<EmployeeWorkInfo> getEmployeeWorkInfoList1(Date from, Date to, Integer did) {
        ArrayList<EmployeeWorkInfo> employeeWorkInfoList = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT\n"
                + "    e.eid,\n"
                + "    e.ename,\n"
                + "    e.salary,\n"
                + "    pd.date,\n"
                + "    p.pname AS ProductName,\n"
                + "    ph.estimatedeffort AS EstimatedEffort,\n"
                + "    COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN w.quantity ELSE 0 END), 0) AS TotalQuantityAssignedPerDay,\n"
                + "    COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN a.actualquantity ELSE 0 END), 0) AS TotalActualQuantity,\n"
                + "    CASE \n"
                + "        WHEN COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN w.quantity ELSE 0 END), 0) = 0 \n"
                + "        THEN 0 \n"
                + "        ELSE COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN a.actualquantity ELSE 0 END), 0) /\n"
                + "        CAST(COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN w.quantity ELSE 0 END), 0) AS DECIMAL(10, 2))\n"
                + "    END AS AverageAlpha,\n"
                + "    COUNT(CASE WHEN pd.date BETWEEN ? AND ? THEN s.sname ELSE NULL END) AS TotalShifts,\n"
                + "    (8 * COUNT(CASE WHEN pd.date BETWEEN ? AND ? THEN s.sname ELSE NULL END) * e.salary * COALESCE(ph.estimatedeffort, 0) * \n"
                + "    COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN w.quantity ELSE 0 END), 0) * \n"
                + "    CASE \n"
                + "        WHEN COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN w.quantity ELSE 0 END), 0) = 0 \n"
                + "        THEN 0 \n"
                + "        ELSE COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN a.actualquantity ELSE 0 END), 0) /\n"
                + "        CAST(COALESCE(SUM(CASE WHEN pd.date BETWEEN ? AND ? THEN w.quantity ELSE 0 END), 0) AS DECIMAL(10, 2))\n"
                + "    END) AS CalculatedSalary\n"
                + "FROM Employees e\n"
                + "LEFT JOIN WorkAssignments w ON e.eid = w.eid\n"
                + "LEFT JOIN PlanDetails pd ON w.pdid = pd.pdid AND pd.date BETWEEN ? AND ?\n"
                + "LEFT JOIN Shifts s ON pd.sid = s.sid\n"
                + "LEFT JOIN PlanHeaders ph ON pd.phid = ph.phid AND pd.date BETWEEN ? AND ?\n"
                + "LEFT JOIN Products p ON ph.pid = p.pid\n"
                + "LEFT JOIN Attendances a ON w.waid = a.waid\n";

        if (did != null) {
            sql += "WHERE e.did = ?";
        }

        sql += "GROUP BY\n"
                + "    e.eid, e.ename, e.salary, pd.date, p.pname, ph.estimatedeffort\n"
                + "ORDER BY\n"
                + "    CASE WHEN pd.date IS NULL THEN 1 ELSE 0 END,\n"
                + "    CASE WHEN p.pname IS NULL THEN 1 ELSE 0 END,\n"
                + "    e.eid, pd.date, p.pname";

        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, from);
            stm.setDate(2, to);
            stm.setDate(3, from);
            stm.setDate(4, to);
            stm.setDate(5, from);
            stm.setDate(6, to);
            stm.setDate(7, from);
            stm.setDate(8, to);
            stm.setDate(9, from);
            stm.setDate(10, to);
            stm.setDate(11, from);
            stm.setDate(12, to);
            stm.setDate(13, from);
            stm.setDate(14, to);
            stm.setDate(15, from);
            stm.setDate(16, to);
            stm.setDate(17, from);
            stm.setDate(18, to);
            stm.setDate(19, from);
            stm.setDate(20, to);
            stm.setDate(21, from);
            stm.setDate(22, to);
            stm.setDate(23, from);
            stm.setDate(24, to);
            stm.setDate(25, from);
            stm.setDate(26, to);
            if (did != null) {
                stm.setInt(27, did);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeWorkInfo info = new EmployeeWorkInfo();

                // Gán dữ liệu từ ResultSet vào EmployeeWorkInfo
                info.setEid(rs.getInt("eid"));
                info.setEname(rs.getString("ename"));
                info.setSalary(rs.getFloat("salary"));
                info.setDate(rs.getDate("date"));
                info.setProductName(rs.getString("ProductName"));
                info.setEstimatedEffort(rs.getFloat("EstimatedEffort"));
                info.setQuantityAssigned(rs.getInt("TotalQuantityAssignedPerDay"));
                info.setActualQuantity(rs.getInt("TotalActualQuantity"));
                info.setAlpha(rs.getFloat("AverageAlpha"));
                info.setTotalShifts(rs.getInt("TotalShifts"));
                info.setCalculatedSalary(rs.getFloat("CalculatedSalary"));

                // Thêm đối tượng EmployeeWorkInfo vào danh sách
                employeeWorkInfoList.add(info);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeeWorkInfoList;
    }

    public ArrayList<EmployeeWorkInfo> getEmployeeSalary() {
        ArrayList<EmployeeWorkInfo> employeeWorkInfoList = new ArrayList<>();

        String sql = "SELECT \n"
                + "    e.eid,\n"
                + "    e.ename,\n"
                + "    SUM(SUM(a.actualquantity)) OVER (PARTITION BY e.eid) AS TotalActualQuantity,  \n"
                + "    SUM((8 * COUNT(s.sname) * e.salary * COALESCE(ph.estimatedeffort, 0) * \n"
                + "        SUM(a.actualquantity) * COALESCE(AVG(a.alpha), 1))) \n"
                + "        OVER (PARTITION BY e.eid) AS TotalSalary\n"
                + "FROM Employees e\n"
                + "LEFT JOIN WorkAssignments w ON e.eid = w.eid\n"
                + "LEFT JOIN PlanDetails pd ON w.pdid = pd.pdid\n"
                + "LEFT JOIN Shifts s ON pd.sid = s.sid\n"
                + "LEFT JOIN PlanHeaders ph ON pd.phid = ph.phid\n"
                + "LEFT JOIN Products p ON ph.pid = p.pid\n"
                + "LEFT JOIN Attendances a ON w.waid = a.waid\n"
                + "GROUP BY \n"
                + "    e.eid, e.ename, e.salary, pd.date, p.pname, ph.estimatedeffort\n"
                + "ORDER BY \n"
                + "    CASE WHEN e.eid IS NULL THEN 1 ELSE 0 END, \n"
                + "    CASE WHEN e.ename IS NULL THEN 1 ELSE 0 END;";

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            EmployeeWorkInfo info = new EmployeeWorkInfo();
            info.setEid(-1);

            while (rs.next()) {
                int eid = rs.getInt("eid");
                if (eid != info.getEid()) {
                    info = new EmployeeWorkInfo();
                    // Gán dữ liệu từ ResultSet vào EmployeeWorkInfo
                    info.setEid(rs.getInt("eid"));
                    info.setEname(rs.getString("ename"));
                    info.setActualQuantity(rs.getInt("TotalActualQuantity"));
                    info.setCalculatedSalary(rs.getFloat("TotalSalary"));

                    // Thêm đối tượng EmployeeWorkInfo vào danh sách
                    employeeWorkInfoList.add(info);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeeWorkInfoList;
    }

    public ArrayList<EmployeeWorkInfo> getEmployeeSalaryInMonth(Date from, Date to, Integer did) {
        ArrayList<EmployeeWorkInfo> employeeWorkInfoList = new ArrayList<>();
        PreparedStatement stm = null;

        String sql = "SELECT \n"
                + "    e.eid,\n"
                + "    e.ename,\n"
                + "    SUM(SUM(a.actualquantity)) OVER (PARTITION BY e.eid) AS TotalActualQuantity,  \n"
                + "    SUM((8 * COUNT(s.sname) * e.salary * COALESCE(ph.estimatedeffort, 0) * \n"
                + "        COALESCE(SUM(w.quantity), 0) * COALESCE(AVG(a.alpha), 1))) \n"
                + "        OVER (PARTITION BY e.eid) AS TotalSalary\n"
                + "FROM Employees e\n"
                + "LEFT JOIN WorkAssignments w ON e.eid = w.eid\n"
                + "LEFT JOIN PlanDetails pd ON w.pdid = pd.pdid\n"
                + "LEFT JOIN Shifts s ON pd.sid = s.sid\n"
                + "LEFT JOIN PlanHeaders ph ON pd.phid = ph.phid\n"
                + "LEFT JOIN Products p ON ph.pid = p.pid\n"
                + "LEFT JOIN Attendances a ON w.waid = a.waid\n"
                + "WHERE ((pd.date BETWEEN ? AND ?) OR pd.date IS NULL)";

        if (did != null) {
            sql += " AND e.did = ?\n";
        }

        sql += "GROUP BY \n"
                + "    e.eid, e.ename, e.salary, pd.date, p.pname, ph.estimatedeffort\n"
                + "ORDER BY \n"
                + "    CASE WHEN e.eid IS NULL THEN 1 ELSE 0 END, \n"
                + "    CASE WHEN e.ename IS NULL THEN 1 ELSE 0 END;";

        try {
            stm = connection.prepareStatement(sql);

            stm.setDate(1, from);
            stm.setDate(2, to);
            if (did != null) {
                stm.setInt(3, did);
            }

            ResultSet rs = stm.executeQuery();

            EmployeeWorkInfo info = new EmployeeWorkInfo();
            info.setEid(-1);

            while (rs.next()) {
                int eid = rs.getInt("eid");
                if (eid != info.getEid()) {
                    info = new EmployeeWorkInfo();
                    // Gán dữ liệu từ ResultSet vào EmployeeWorkInfo
                    info.setEid(rs.getInt("eid"));
                    info.setEname(rs.getString("ename"));
                    info.setActualQuantity(rs.getInt("TotalActualQuantity"));
                    info.setCalculatedSalary(rs.getFloat("TotalSalary"));

                    // Thêm đối tượng EmployeeWorkInfo vào danh sách
                    employeeWorkInfoList.add(info);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeeWorkInfoList;
    }

    public ArrayList<EmployeeWorkInfo> getEmployeeSalaryInMonth1(Date from, Date to, Integer did) {
        ArrayList<EmployeeWorkInfo> employeeWorkInfoList = new ArrayList<>();
        PreparedStatement stm = null;

        String sql = "SELECT \n"
                + "    e.eid,\n"
                + "    e.ename,\n"
                + "    COALESCE(SUM(SUM(a.actualquantity)) OVER (PARTITION BY e.eid), 0) AS TotalActualQuantity,  \n"
                + "    COALESCE(SUM((8 * COUNT(s.sname) * e.salary * COALESCE(ph.estimatedeffort, 0) * \n"
                + "        COALESCE(SUM(w.quantity), 0) * COALESCE(AVG(a.alpha), 1))) \n"
                + "        OVER (PARTITION BY e.eid), 0) AS TotalSalary\n"
                + "FROM Employees e\n"
                + "LEFT JOIN WorkAssignments w ON e.eid = w.eid\n"
                + "LEFT JOIN PlanDetails pd ON w.pdid = pd.pdid AND ((pd.date BETWEEN ? AND ?) OR pd.date IS NULL)\n"
                + "LEFT JOIN Shifts s ON pd.sid = s.sid\n"
                + "LEFT JOIN PlanHeaders ph ON pd.phid = ph.phid\n"
                + "LEFT JOIN Products p ON ph.pid = p.pid\n"
                + "LEFT JOIN Attendances a ON w.waid = a.waid\n";

        if (did != null) {
            sql += "WHERE e.did = ?\n";
        }
        sql += "GROUP BY \n"
                + "    e.eid, e.ename, e.salary, pd.date, p.pname, ph.estimatedeffort\n"
                + "ORDER BY \n"
                + "    CASE WHEN e.eid IS NULL THEN 1 ELSE 0 END, \n"
                + "    CASE WHEN e.ename IS NULL THEN 1 ELSE 0 END;";

        try {
            stm = connection.prepareStatement(sql);

            stm.setDate(1, from);
            stm.setDate(2, to);
            if (did != null) {
                stm.setInt(3, did);
            }

            ResultSet rs = stm.executeQuery();

            EmployeeWorkInfo info = new EmployeeWorkInfo();
            info.setEid(-1);

            while (rs.next()) {
                int eid = rs.getInt("eid");
                if (eid != info.getEid()) {
                    info = new EmployeeWorkInfo();
                    // Gán dữ liệu từ ResultSet vào EmployeeWorkInfo
                    info.setEid(rs.getInt("eid"));
                    info.setEname(rs.getString("ename"));
                    info.setActualQuantity(rs.getInt("TotalActualQuantity"));
                    info.setCalculatedSalary(rs.getFloat("TotalSalary"));

                    // Thêm đối tượng EmployeeWorkInfo vào danh sách
                    employeeWorkInfoList.add(info);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeeWorkInfoList;
    }

    public ArrayList<EmployeeWorkInfo> getEmployee() {
        ArrayList<EmployeeWorkInfo> emps = new ArrayList<>();

        String sql = "select * from Employees";

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                EmployeeWorkInfo info = new EmployeeWorkInfo();
                // Gán dữ liệu từ ResultSet vào EmployeeWorkInfo
                info.setEid(rs.getInt("eid"));
                info.setEname(rs.getString("ename"));

                emps.add(info);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return emps;
    }

}
