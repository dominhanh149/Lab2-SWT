/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author FPTSHOP
 */
public class WorkAssignment {
    private int id;
    private ProductionPlanDetail details;
    private Employee emp;
    private int quantity;
    private ArrayList<Attendent> att = new ArrayList<>();

    public ArrayList<Attendent> getAtt() {
        return att;
    }

    public void setAtt(ArrayList<Attendent> att) {
        this.att = att;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductionPlanDetail getDetails() {
        return details;
    }

    public void setDetails(ProductionPlanDetail details) {
        this.details = details;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
