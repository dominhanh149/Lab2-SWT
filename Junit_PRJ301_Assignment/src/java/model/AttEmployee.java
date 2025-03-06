/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author FPTSHOP
 */
public class AttEmployee {
    private int id;
    private String name;
    private float salary;
    private ArrayList<AttProduct> prds = new ArrayList<>();
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public ArrayList<AttProduct> getPrds() {
        return prds;
    }

    public void setPrds(ArrayList<AttProduct> prds) {
        this.prds = prds;
    }
    

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
