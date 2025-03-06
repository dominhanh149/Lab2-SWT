/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package algorithm;

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
public class BubbleSortTest {

    public BubbleSortTest() {
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
     * Test trường hợp mảng có các phần tử hỗn độn và sắp xếp theo thứ tự tăng
     * dần.
     */
    @Test
    public void testBubbleSortAscending() {
        System.out.println("testBubbleSortAscending");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 0;
        int endIndex = arr.length - 1;
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả
        int[] expected = {11, 12, 22, 25, 34, 64, 90}; // Mảng đã sắp xếp
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng có các phần tử hỗn độn và sắp xếp theo thứ tự giảm
     * dần.
     */
    @Test
    public void testBubbleSortDescending() {
        System.out.println("testBubbleSortDescending");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 0;
        int endIndex = arr.length - 1;
        boolean ascending = false;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả
        int[] expected = {90, 64, 34, 25, 22, 12, 11}; // Mảng đã sắp xếp
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng đã được sắp xếp theo thứ tự tăng dần.
     */
    @Test
    public void testBubbleSortAlreadySorted() {
        System.out.println("testBubbleSortAlreadySorted");
        int[] arr = {11, 12, 22, 25, 34, 64, 90}; // Mảng đã sắp xếp
        int startIndex = 0;
        int endIndex = arr.length - 1;
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả (mảng không thay đổi)
        int[] expected = {11, 12, 22, 25, 34, 64, 90}; // Mảng đã sắp xếp
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng đã được sắp xếp theo thứ tự giảm dần.
     */
    @Test
    public void testBubbleSortAlreadySortedDescending() {
        System.out.println("testBubbleSortAlreadySortedDescending");
        int[] arr = {90, 64, 34, 25, 22, 12, 11}; // Mảng đã sắp xếp giảm dần
        int startIndex = 0;
        int endIndex = arr.length - 1;
        boolean ascending = false;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả (mảng không thay đổi)
        int[] expected = {90, 64, 34, 25, 22, 12, 11}; // Mảng đã sắp xếp
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng rỗng.
     */
    @Test
    public void testBubbleSortEmptyArray() {
        System.out.println("testBubbleSortEmptyArray");
        int[] arr = {}; // Mảng rỗng
        int startIndex = 0;
        int endIndex = arr.length - 1;
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả (mảng vẫn rỗng)
        int[] expected = {}; // Mảng rỗng
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng có một phần tử.
     */
    @Test
    public void testBubbleSortSingleElement() {
        System.out.println("testBubbleSortSingleElement");
        int[] arr = {10}; // Mảng có một phần tử
        int startIndex = 0;
        int endIndex = arr.length - 1;
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả (mảng không thay đổi)
        int[] expected = {10}; // Mảng có một phần tử
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng với startIndex và endIndex chỉ định phạm vi sắp xếp
     * trong mảng.
     */
    @Test
    public void testBubbleSortWithRange() {
        System.out.println("testBubbleSortWithRange");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 2;  // Bắt đầu từ vị trí index 2 (số 25)
        int endIndex = 5;    // Kết thúc tại vị trí index 5 (số 11)
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả
        int[] expected = {64, 34, 11, 12, 22, 25, 90}; // Mảng đã sắp xếp trong phạm vi từ index 2 đến 5
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp với startIndex là 0 và endIndex là phần tử cuối cùng.
     */
    @Test
    public void testBubbleSortWithFullArray() {
        System.out.println("testBubbleSortWithFullArray");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 0;   // Bắt đầu từ index 0
        int endIndex = arr.length - 1;  // Kết thúc tại phần tử cuối cùng
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả
        int[] expected = {11, 12, 22, 25, 34, 64, 90}; // Mảng đã sắp xếp
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp startIndex > endIndex.
     */
    @Test
    public void testBubbleSortInvalidRange() {
        System.out.println("testBubbleSortInvalidRange");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 5;   // Start index ở vị trí 5 (số 11)
        int endIndex = 2;     // End index ở vị trí 2 (số 25)
        boolean ascending = true;

        // Gọi hàm bubbleSort, đây là trường hợp không hợp lệ, nên không sắp xếp gì
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả (mảng không thay đổi)
        int[] expected = {64, 34, 25, 12, 22, 11, 90}; // Mảng không thay đổi
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp chỉ có một phần tử trong phạm vi [startIndex, endIndex].
     */
    @Test
    public void testBubbleSortWithSingleElement() {
        System.out.println("testBubbleSortWithSingleElement");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 3;  // Chỉ định phạm vi bắt đầu từ index 3 (số 12)
        int endIndex = 3;    // Chỉ định kết thúc tại index 3 (số 12)
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả (mảng không thay đổi)
        int[] expected = {64, 34, 25, 12, 22, 11, 90}; // Mảng không thay đổi
        assertArrayEquals(expected, arr);
    }

    /**
     * Test trường hợp mảng chỉ có hai phần tử trong phạm vi [startIndex,
     * endIndex].
     */
    @Test
    public void testBubbleSortWithTwoElements() {
        System.out.println("testBubbleSortWithTwoElements");
        int[] arr = {64, 34, 25, 12, 22, 11, 90}; // Mảng đầu vào
        int startIndex = 2;  // Bắt đầu từ index 2 (số 25)
        int endIndex = 3;    // Kết thúc tại index 3 (số 12)
        boolean ascending = true;

        // Gọi hàm bubbleSort
        BubbleSort.bubbleSort(arr, startIndex, endIndex, ascending);

        // Kiểm tra kết quả
        int[] expected = {64, 34, 12, 25, 22, 11, 90}; // Mảng đã sắp xếp trong phạm vi từ index 2 đến 3
        assertArrayEquals(expected, arr);
    }
}
