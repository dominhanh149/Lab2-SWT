package dal;

import dal.ProductionPlanDBContext;
import model.Department;
import model.ProductionPlan;
import model.ProductionPlanHeader;
import model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Date;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ProductionPlanDBContextTest {

    private ProductionPlanDBContext dbContext;

    @Before
    public void setUp() {
        dbContext = new ProductionPlanDBContext();
    }

    @After
    public void tearDown() {
        dbContext = null;
    }

    @Test
    public void testInsert() {
        // Arrange
        ProductionPlan plan = new ProductionPlan();
        plan.setName("Test Plan");
        plan.setStart(Date.valueOf("2025-03-01"));
        plan.setEnd(Date.valueOf("2025-03-10"));
        
        Department department = new Department();
        department.setId(1);  // Giả sử tồn tại department có id=1
        plan.setDept(department);

        // Tạo danh sách header
        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();
        ProductionPlanHeader header = new ProductionPlanHeader();
        header.setQuantity(100);
        header.setEstimatedeffort(20.5f);
        
        Product product = new Product();
        product.setPid(1);  // Giả sử tồn tại product có id=1
        header.setProduct(product);
        
        headers.add(header);
        plan.setHeaders(headers);

        // Act
        dbContext.insert(plan);

        // Assert
        assertNotNull("Plan ID should not be null after insertion", plan.getId());
    }

//    @Test
//    public void testUpdate() {
//        // Arrange
//        ProductionPlan plan = dbContext.get(1);
//        if (plan == null) {
//            plan = new ProductionPlan();
//            plan.setName("Initial Plan");
//            plan.setStart(Date.valueOf("2025-03-01"));
//            plan.setEnd(Date.valueOf("2025-03-10"));
//            
//            Department department = new Department();
//            department.setId(1);
//            plan.setDept(department);
//            
//            dbContext.insert(plan);
//            plan = dbContext.get(1);
//            assertNotNull("Plan should not be null after insert", plan);
//        }
//
//        plan.setName("Updated Plan");
//        plan.setStart(Date.valueOf("2025-04-01"));
//        plan.setEnd(Date.valueOf("2025-04-15"));
//        
//        Department newDepartment = new Department();
//        newDepartment.setId(2);
//        plan.setDept(newDepartment);
//
//        // Act
//        dbContext.update(plan);
//
//        // Assert
//        ProductionPlan updatedPlan = dbContext.get(1);
//        assertNotNull("Updated plan should not be null", updatedPlan);
//        assertEquals("Updated Plan", updatedPlan.getName());
//        assertEquals(Date.valueOf("2025-04-01"), updatedPlan.getStart());
//        assertEquals(Date.valueOf("2025-04-15"), updatedPlan.getEnd());
//        assertEquals(2, updatedPlan.getDept().getId());
//    }

//    @Test
//    public void testDelete() {
//        // Arrange
//        ProductionPlan plan = dbContext.get(1);
//        if (plan == null) {
//            plan = new ProductionPlan();
//            plan.setName("Test Plan for Deletion");
//            plan.setStart(Date.valueOf("2025-03-01"));
//            plan.setEnd(Date.valueOf("2025-03-10"));
//            
//            Department department = new Department();
//            department.setId(1);
//            plan.setDept(department);
//            
//            dbContext.insert(plan);
//            plan = dbContext.get(1);
//            assertNotNull("Plan should not be null after insert", plan);
//        }
//
//        // Act
//        dbContext.delete(plan);
//
//        // Assert
//        ProductionPlan deletedPlan = dbContext.get(1);
//        assertNull("Plan should be null after deletion", deletedPlan);
//    }
}
