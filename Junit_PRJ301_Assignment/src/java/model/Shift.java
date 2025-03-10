/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author FPTSHOP
 */
public class Shift {
    private int sid;
    private String sname;
    private Date starttime;
    private Date endtime;
    private ArrayList<ProductionPlanDetail> details = new ArrayList<>();

    public ArrayList<ProductionPlanDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<ProductionPlanDetail> details) {
        this.details = details;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
    
    
}
