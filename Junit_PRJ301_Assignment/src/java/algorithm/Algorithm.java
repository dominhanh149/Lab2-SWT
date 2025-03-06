/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algorithm;

/**
 *
 * @author FPTSHOP
 */
public class Algorithm {
    public static boolean isPalindrome(String s){
        int left = 0;
        int right = s.length() - 1;
        
        if(s.length() == 0 || s.isEmpty()){
            return false;
        }
        
        while (left < right) {            
            if(s.charAt(left) != s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    public static void main(String[] args) {
        String x = "hello";
        System.out.println(isPalindrome(x));
    }

}
