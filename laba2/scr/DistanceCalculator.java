import java.util.Scanner;

public class DistanceCalculator {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       System.out.print("Введите x1: ");
        double x1 = scanner.nextDouble();
        System.out.print("Введите y1: ");
        double y1 = scanner.nextDouble();
        System.out.print("Введите x2: ");
        double x2 = scanner.nextDouble();
        System.out.print("Введите y2: ");
        double y2 = scanner.nextDouble();

        System.out.println("Расстояние между точками: " + distance(x1, y1, x2, y2));

        scanner.close();
    }
}