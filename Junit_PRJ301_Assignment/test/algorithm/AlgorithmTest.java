/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package algorithm;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author FPTSHOP
 */
public class AlgorithmTest {
    
    
    @Test
    public void testIsPalindrome_TrueCase() {
        String str = "madam";
        assertTrue(str + " is not Symmetry", Algorithm.isPalindrome(str));
        System.out.println("Passed: " + str);
    }

    @Test
    public void testIsPalindrome_FalseCase() {
        String str = "hello";
        assertFalse(str + " is Symmetry", Algorithm.isPalindrome(str));
        System.out.println("Passed: " + str);
    }

    @Test
    public void testIsPalindrome_EmptyString() {
        String str = "";
        assertFalse(str + " is not Symmetry", Algorithm.isPalindrome(str));
        System.out.println("Passed: " + str);
    }
    
    @Test
    public void testIsPalindrome_OneChar() {
        String str = "x";
        assertTrue(str + " is not Symmetry", Algorithm.isPalindrome(str));
        System.out.println("Passed: " + str);
    }
    
    @Test
    public void testIsPalindrome_CaseSensitive() {
        String str = "Madam";
        assertFalse(str + " is not Symmetry", Algorithm.isPalindrome(str));
        System.out.println("Passed: " + str);
    }
    
    @Test
    public void testIsPalindrome_Space() {
        String str = "a man a plan a canal panama";
        String cleanedStr = str.replaceAll("\\s+", "").toLowerCase();
        assertTrue(str + " is not Symmetry", Algorithm.isPalindrome(cleanedStr));
        System.out.println("Passed: " + str);
    }
}
