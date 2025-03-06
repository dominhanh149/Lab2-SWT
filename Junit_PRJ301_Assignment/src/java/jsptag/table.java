/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsptag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author FPTSHOP
 */
public class table extends SimpleTagSupport {

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductionSchedulingSystem_DB;trustServerCertificate=true;";
        String user = "sa";
        String password = "123";

        JspWriter out = getJspContext().getOut();

        // Check if tableName is provided
        if (tableName == null || tableName.trim().isEmpty()) {
            out.write("Table name is not provided.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Prepare SQL query
            String sql = "SELECT * FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            // Get metadata to fetch column information
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Generate HTML table with column names as header
            out.write("<table border='1px'>");
            out.write("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                out.write("<th>" + metaData.getColumnName(i) + "</th>");
            }
            out.write("</tr>");

            // Print rows of data
            while (rs.next()) {
                out.write("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    out.write("<td>" + (rs.getString(i) != null ? rs.getString(i) : "N/A") + "</td>");
                }
                out.write("</tr>");
            }
            out.write("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new JspException("Database error: " + e.getMessage());
        }

    }

}
