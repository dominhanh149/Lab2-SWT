package algorithm;

public class QuadraticEquation {

    /**
     * Giải phương trình bậc 2: ax^2 + bx + c = 0
     * @param a hệ số a (phải khác 0)
     * @param b hệ số b
     * @param c hệ số c
     * @return Mảng chứa nghiệm của phương trình (có thể có 0, 1 hoặc 2 nghiệm)
     */
    public static double[] solveQuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("Hệ số a phải khác 0");
        }

        double delta = b * b - 4 * a * c;

        if (delta > 0) {
            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
            double x2 = (-b - Math.sqrt(delta)) / (2 * a);
            return new double[]{x1, x2};
        } else if (delta == 0) {
            double x = -b / (2 * a);
            return new double[]{x};
        } else {
            return new double[]{}; // Vô nghiệm
        }
    }
    
    public static void main(String[] args) {
        double[] result = solveQuadraticEquation(1, -3, 2);
        if (result.length == 2) {
            System.out.println("Nghiệm: x1 = " + result[0] + ", x2 = " + result[1]);
        } else if (result.length == 1) {
            System.out.println("Nghiệm kép: x = " + result[0]);
        } else {
            System.out.println("Phương trình vô nghiệm.");
        }
    }
}
