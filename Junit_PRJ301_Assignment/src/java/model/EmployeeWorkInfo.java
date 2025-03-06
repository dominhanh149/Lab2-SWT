package model;

import java.sql.Date;
import java.text.DecimalFormat;

public class EmployeeWorkInfo {
    private int eid;
    private String ename;
    private float salary;
    private String shiftName;
    private Date date;
    private String productName;
    private float estimatedEffort;
    private int quantityAssigned;
    private int actualQuantity;
    private float alpha;
    private int totalShifts;
    private float calculatedSalary;

    // Định dạng số thập phân với 2 chữ số sau dấu phẩy
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getters and Setters
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    // Thêm định dạng vào getter của salary
    public String getSalary() {
        return DECIMAL_FORMAT.format(salary);
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getEstimatedEffort() {
        return DECIMAL_FORMAT.format(estimatedEffort);
    }

    public void setEstimatedEffort(float estimatedEffort) {
        this.estimatedEffort = estimatedEffort;
    }

    public int getQuantityAssigned() {
        return quantityAssigned;
    }

    public void setQuantityAssigned(int quantityAssigned) {
        this.quantityAssigned = quantityAssigned;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getAlpha() {
        return DECIMAL_FORMAT.format(alpha);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getTotalShifts() {
        return totalShifts;
    }

    public void setTotalShifts(int totalShifts) {
        this.totalShifts = totalShifts;
    }

    public String getCalculatedSalary() {
        return DECIMAL_FORMAT.format(calculatedSalary);
    }

    public void setCalculatedSalary(float calculatedSalary) {
        this.calculatedSalary = calculatedSalary;
    }
}
