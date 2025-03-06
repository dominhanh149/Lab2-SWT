/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Date;
import java.util.ArrayList;
/**
 *
 * @author FPTSHOP
 */
public class ProductionPlanDetail {
    private int pdid;
    private ProductionPlanHeader pheader;
    private Shift shift;
    private Date date;
    private int quantity;
    private ArrayList<WorkAssignment> works = new ArrayList<>();

    public ArrayList<WorkAssignment> getWorks() {
        return works;
    }

    public void setWorks(ArrayList<WorkAssignment> works) {
        this.works = works;
    }

    public int getPdid() {
        return pdid;
    }

    public void setPdid(int pdid) {
        this.pdid = pdid;
    }

    public ProductionPlanHeader getPheader() {
        return pheader;
    }

    public void setPheader(ProductionPlanHeader pheader) {
        this.pheader = pheader;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductionPlanDetail(int pdid, ProductionPlanHeader pheader, Shift shift, Date date, int quantity) {
        this.pdid = pdid;
        this.pheader = pheader;
        this.shift = shift;
        this.date = date;
        this.quantity = quantity;
    }

    public ProductionPlanDetail() {
    }
    
    
}
