/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller.manage;


import dal.ProductionPlanDBContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Department;
import model.ProductionPlan;
import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductionPlanDetailControllerTest {

    public ProductionPlanDetailControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of processRequest method, of class ProductionPlanDetailController.
     */
    @Test
    public void testProcessRequest() throws Exception {
        System.out.println("processRequest");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ProductionPlanDetailController instance = new ProductionPlanDetailController();
        instance.processRequest(request, response);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doAuthorizedGet method, of class ProductionPlanDetailController.
     */
    @Test
    void testDoAuthorizedGet() throws Exception {

        // Đóng kết nối sau khi test
    }

    /**
     * Test of doAuthorizedPost method, of class ProductionPlanDetailController.
     */
    @Test
    public void testDoAuthorizedPost() throws Exception {
        System.out.println("doAuthorizedPost");
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        User user = null;
        ProductionPlanDetailController instance = new ProductionPlanDetailController();
        instance.doAuthorizedPost(req, resp, user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNumber method, of class ProductionPlanDetailController.
     */
    @Test
    public void testIsNumber() {
        System.out.println("isNumber");
        String str = "";
        ProductionPlanDetailController instance = new ProductionPlanDetailController();
        boolean expResult = false;
        boolean result = instance.isNumber(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDigit method, of class ProductionPlanDetailController.
     */
    @Test
    public void testIsDigit() {
        System.out.println("isDigit");
        String n = "";
        ProductionPlanDetailController instance = new ProductionPlanDetailController();
        boolean expResult = false;
        boolean result = instance.isDigit(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
