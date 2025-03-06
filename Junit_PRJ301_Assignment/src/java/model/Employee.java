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
public class Employee {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private Department dept;
    private float salary;
    private ArrayList<WorkAssignment> assignments = new ArrayList<>();

    public ArrayList<WorkAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<WorkAssignment> assignments) {
        this.assignments = assignments;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Employee() {
    }

    public Employee(int id, String name, String phoneNumber, String address, Department dept, float salary) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dept = dept;
        this.salary = salary;
    }

    

    
}
