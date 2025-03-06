package algorithm;

import Algorithm.Fibonacci;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author admin
 */
public class FibonacciTest {
    
    public FibonacciTest() {
    }

    // Test hợp lệ (valid cases)
    @Test
    public void testFibonacci_ValidCases() {
        assertEquals(0, Fibonacci.fibonacci(0)); // Trường hợp n = 0
        assertEquals(1, Fibonacci.fibonacci(1)); // Trường hợp n = 1
        assertEquals(1, Fibonacci.fibonacci(2)); // Trường hợp n = 2
        assertEquals(2, Fibonacci.fibonacci(3)); // Trường hợp n = 3
        assertEquals(3, Fibonacci.fibonacci(4)); // Trường hợp n = 4
        assertEquals(5, Fibonacci.fibonacci(5)); // Trường hợp n = 5
        assertEquals(8, Fibonacci.fibonacci(6)); // Trường hợp n = 6
        assertEquals(13, Fibonacci.fibonacci(7)); // Trường hợp n = 7
        assertEquals(21, Fibonacci.fibonacci(8)); // Trường hợp n = 8
        assertEquals(55, Fibonacci.fibonacci(10)); // Trường hợp n = 10
    }


    // Test hàm main (kiểm tra xem nó có chạy mà không lỗi không)
    @Test
    public void testMain() {
        String[] args = null;
        Fibonacci.main(args);
    }
}