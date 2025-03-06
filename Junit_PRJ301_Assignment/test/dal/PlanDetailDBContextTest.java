/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import model.Shift;

/**
 *
 * @author Admin
 */
public class PlanDetailDBContextTest {

    private PlanDetailDBContext PlanDetailDBContext;
    private Connection connection;

    public PlanDetailDBContextTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        PlanDetailDBContext = new PlanDetailDBContext();
        connection = new PlanDetailDBContext().connection;

        // Xóa dữ liệu test trước khi chạy test
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Test of insert method, of class PlanDetailDBContext.
     */
    @Test
    public void testInsert_ProductionPlanDetail() throws SQLException {
        System.out.println("insert");

        // Tạo một đối tượng ProductionPlanDetail hợp lệ để chèn vào cơ sở dữ liệu
        ProductionPlanDetail model = new ProductionPlanDetail();
        ProductionPlanHeader header = new ProductionPlanHeader();
        header.setId(2);  // Đảm bảo rằng ID này có tồn tại trong cơ sở dữ liệu của bạn
        model.setPheader(header);

        Shift shift = new Shift();
        shift.setSid(1);  // Đảm bảo rằng shift này có tồn tại trong cơ sở dữ liệu của bạn
        model.setShift(shift);

        model.setDate(Date.valueOf("2024-10-01"));  // Ngày hợp lệ
        model.setQuantity(10);  // Số lượng hợp lệ

        // Tạo đối tượng PlanDetailDBContext và gọi phương thức insert
        PlanDetailDBContext instance = new PlanDetailDBContext();
        instance.insert(model);

        // Kiểm tra xem bản ghi có tồn tại trong DB không
        String sql = "SELECT COUNT(*) FROM PlanDetails WHERE phid = ? AND sid = ? AND date = ? AND quantity = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, model.getPheader().getId());  // Sử dụng phid từ đối tượng model
            stm.setInt(2, model.getShift().getSid());   // Sử dụng sid từ đối tượng model
            stm.setDate(3, model.getDate());            // Sử dụng ngày từ đối tượng model
            stm.setInt(4, model.getQuantity());        // Sử dụng quantity từ đối tượng model

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Số bản ghi đã được chèn vào DB: " + count); // Debug
                    assertEquals(1, count);  // Kiểm tra xem bản ghi đã được chèn hay chưa
                }
            }
        }
    }


    private void clearTestData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
