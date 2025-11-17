import java.util.Scanner;

public class Recursion {

    public static double power(double a, int n) {
        if (n == 0) {
            return 1; 
        }
        return a * power(a, n - 1); 
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double a = scanner.nextDouble(); 
        int n = scanner.nextInt();       

        System.out.println(power(a, n));

        scanner.close();
    }
}