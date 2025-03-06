/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algorithm;

/**
 *
 * @author Admin
 */
public class BubbleSort {

    // Thuật toán Bubble Sort với 4 tham số đầu vào
    public static void bubbleSort(int[] arr, int startIndex, int endIndex, boolean ascending) {
        // Kiểm tra đầu vào hợp lệ
        if (arr == null || arr.length == 0 || startIndex < 0 || endIndex >= arr.length || startIndex >= endIndex) {
            System.out.println("Invalid input");
            return;
        }

        // Thuật toán Bubble Sort
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = startIndex; j < endIndex - i + startIndex; j++) {
                if ((ascending && arr[j] > arr[j + 1]) || (!ascending && arr[j] < arr[j + 1])) {
                    // Hoán đổi nếu không theo đúng thứ tự
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Hàm in mảng ra màn hình
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Dữ liệu thử nghiệm
        int[] arr = {64, 34, 25, 12, 22, 11, 90};

        // In mảng ban đầu
        System.out.println("Original Array:");
        printArray(arr);

        // Gọi bubbleSort để sắp xếp mảng theo thứ tự tăng dần (ascending)
        bubbleSort(arr, 0, arr.length - 1, true);
        System.out.println("Sorted Array in Ascending Order:");
        printArray(arr);

        // Gọi bubbleSort để sắp xếp mảng theo thứ tự giảm dần (descending)
        bubbleSort(arr, 0, arr.length - 1, false);
        System.out.println("Sorted Array in Descending Order:");
        printArray(arr);
    }
    
    
}
