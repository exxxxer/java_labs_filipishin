import java.util.Scanner;

public class NegativeDegree {

    public static double power(double a, int n) {
        double result = 1.0;

        if (n > 0) {
            for (int i = 0; i < n; i++) {
                result *= a;
            }
        } else if (n < 0) {
            for (int i = 0; i < -n; i++) {
                result *= a;
            }
            result = 1.0 / result; 
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double a = scanner.nextDouble(); 
        int n = scanner.nextInt();       

        System.out.println(power(a, n));

        scanner.close();
    }
}