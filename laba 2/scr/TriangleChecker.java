import java.util.Scanner;

public class TriangleChecker {
    public static void triangle(double a, double b, double c) {
        if (a + b > c && a + c > b && b + c >a) {
            System.out.println("Это треугольник");
        } else {
            System.out.println("Это не треугольник");
        }
    }

    public static void
main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите длины трех отрезков:");

        System.out.println("Первая сторона: ");
        double side1 = scanner.nextDouble();

        System.out.println("Вторая сторона: ");
        double side2 = scanner.nextDouble();

        System.out.println("Третья сторона: ");
        double side3 = scanner.nextDouble();

        triangle(side1, side2, side3);

        scanner.close();
    }
}