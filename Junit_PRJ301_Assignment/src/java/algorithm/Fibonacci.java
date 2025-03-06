/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algorithm;

/**
 *
 * @author admin
 */
public class Fibonacci {

    // Phương thức tính số Fibonacci tại vị trí n
    public static long fibonacci(int n) {
    if (n < 0) {
        throw new IllegalArgumentException("n must be a non-negative integer");
    }
    if (n <= 1) {
        return n;
    }
    long a = 0, b = 1;
    for (int i = 2; i <= n; i++) {
        long temp = a + b;
        a = b;
        b = temp;
    }
    return b;
}


    public static void main(String[] args) {
        // Kiểm tra Fibonacci
        int n = 10;
        System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
    }
}

