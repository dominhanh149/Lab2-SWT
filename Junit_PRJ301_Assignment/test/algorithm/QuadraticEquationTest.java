/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package algorithm;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */

public class QuadraticEquationTest {

    @Test
    public void testTwoSolutions() {
        double[] result = QuadraticEquation.solveQuadraticEquation(1, -3, 2);
        assertEquals(2, result.length);
        assertEquals(2.0, result[0], 1e-6);
        assertEquals(1.0, result[1], 1e-6);
    }

    @Test
    public void testOneSolution() {
        double[] result = QuadraticEquation.solveQuadraticEquation(1, -2, 1);
        assertEquals(1, result.length);
        assertEquals(1.0, result[0], 1e-6);
    }

    @Test
    public void testNoSolution() {
        double[] result = QuadraticEquation.solveQuadraticEquation(1, 1, 1);
        assertEquals(0, result.length);
    }

    @Test
    public void testInvalidA() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            QuadraticEquation.solveQuadraticEquation(0, 2, 3);
        });
        assertEquals("Hệ số a phải khác 0", exception.getMessage());
    }
}

