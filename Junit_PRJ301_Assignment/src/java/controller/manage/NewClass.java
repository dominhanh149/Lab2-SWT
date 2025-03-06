/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import dal.AttendentDAO;
import dal.EmployeeDBContext;
import dal.EmployeeWorkInforDAO;
import dal.PlanDetailDBContext;
import dal.ProductionPlanDBContext;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import model.AttEmployee;
import model.AttProduct;
import model.Attendent;
import model.EmployeeWorkInfo;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.WorkAssignment;
import java.sql.Date;
import java.util.ArrayList;
/**
 *
 * @author FPTSHOP
 */
public class NewClass {
    public static void main(String[] args) {
        EmployeeDBContext e1 = new EmployeeDBContext();
        EmployeeWorkInforDAO ed = new EmployeeWorkInforDAO();
        ArrayList<EmployeeWorkInfo> em = ed.getEmployeeWorkInfoList1(Date.valueOf("2024-10-01"), Date.valueOf("2024-10-31"), null);
        ArrayList<EmployeeWorkInfo> emps = new ArrayList<>();
        for (EmployeeWorkInfo e : em) {
            float salary = 0;
            EmployeeWorkInfo emp = new EmployeeWorkInfo();
            emp.setEid(-1);
            if(e.getEid() != emp.getEid()){
                
            }
        }
        
        
    }
    
     public static Date getFirstDayOfMonth() {
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        return Date.valueOf(firstDay);  // Chuyển đổi sang java.sql.Date
    }

    public static Date getLastDayOfMonth() {
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return Date.valueOf(lastDay);  // Chuyển đổi sang java.sql.Date
    }
}
