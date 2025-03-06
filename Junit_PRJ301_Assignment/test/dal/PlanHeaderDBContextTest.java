/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.ProductionPlan;
import model.ProductionPlanHeader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class PlanHeaderDBContextTest {
    
    public PlanHeaderDBContextTest() {
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
     * Test of insert method, of class PlanHeaderDBContext.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        ProductionPlanHeader model = null;
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        instance.insert(model);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class PlanHeaderDBContext.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ProductionPlanHeader model = null;
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        instance.update(model);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class PlanHeaderDBContext.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        ProductionPlanHeader model = null;
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        instance.delete(model);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of list method, of class PlanHeaderDBContext.
     */
    @Test
    public void testList() {
        System.out.println("list");
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        ArrayList<ProductionPlanHeader> expResult = null;
        ArrayList<ProductionPlanHeader> result = instance.list();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class PlanHeaderDBContext.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 0;
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        ProductionPlanHeader expResult = null;
        ProductionPlanHeader result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listHeaders method, of class PlanHeaderDBContext.
     */
    @Test
    public void testListHeaders() {
        System.out.println("listHeaders");
        int plid = 0;
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        ArrayList<ProductionPlan> expResult = null;
        ArrayList<ProductionPlan> result = instance.listHeaders(plid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class PlanHeaderDBContext.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        int plid = 0;
        PlanHeaderDBContext instance = new PlanHeaderDBContext();
        ArrayList<ProductionPlanHeader> expResult = null;
        ArrayList<ProductionPlanHeader> result = instance.getAll(plid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
