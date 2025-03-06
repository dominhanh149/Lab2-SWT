/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsptag;

import dal.EmployeeDBContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;

/**
 *
 * @author FPTSHOP
 */
public class emp extends SimpleTagSupport {

    private Employee e;

    public Employee getE() {
        return e;
    }

    public void setE(Employee e) {
        this.e = e;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        EmployeeDBContext edb = new EmployeeDBContext();
        ArrayList<Employee> emps = edb.listJsp();

        try {
            out.write("<table class='table'>");
            out.write("<tr>");
            out.write("<th>ID</th>");
            out.write("<th>Name</th>");
            out.write("<th>Address</th>");
            out.write("<th>PhoneNumber</th>");
            out.write("<th>Salary</th>");
            out.write("<th>Department</th>");
            out.write("</tr>");
            for (Employee emp : emps) {
                out.write("<tr>");
                out.write("<td>"+ emp.getId() +"</td>");
                out.write("<td>"+ emp.getName()+"</td>");
                out.write("<td>"+ emp.getAddress()+"</td>");
                out.write("<td>"+ emp.getPhoneNumber()+"</td>");
                out.write("<td>"+ emp.getSalary()+"</td>");
                out.write("<td>"+ emp.getDept().getName()+"</td>");
                out.write("</tr>");
            }
            out.write("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
