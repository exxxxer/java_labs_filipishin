package test;

import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry2d.Figure;
import geometry3d.Cylinder;

import exceptions.FigureException;
import exceptions.NonPositiveDimensionException;
import exceptions.NullBaseException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Корректные примеры ===");
        try {
            Figure c = new Circle(3.0);
            Figure r = new Rectangle(2.0, 5.0);
            c.Show();
            r.Show();

            Cylinder cyl1 = new Cylinder(c, 10.0);
            System.out.printf("Объём цилиндра с круглым основанием: %.3f%n", cyl1.Volume());

            Cylinder cyl2 = new Cylinder(r, 4.0);
            System.out.printf("Объём цилиндра с прямоугольным основанием: %.3f%n", cyl2.Volume());
        } catch (FigureException e) {
            System.out.println("Неожиданная ошибка в блоке корректных примеров: " + e.getMessage());
        }

        System.out.println("\n=== Обработка ошибок ===");
        // 1) Неположительный радиус
        try {
            new Circle(0);
        } catch (NonPositiveDimensionException e) {
            System.out.println("Перехвачено NonPositiveDimensionException: " + e.getMessage());
        }

        // 2) Null-основание цилиндра
        try {
            new Cylinder(null, 5);
        } catch (NullBaseException e) {
            System.out.println("Перехвачено NullBaseException: " + e.getMessage());
        } catch (FigureException e) {
            System.out.println("Другое исключение FigureException: " + e.getMessage());
        }

        // 3) Неположительная высота
        try {
            Figure c = new Circle(2);
            new Cylinder(c, -1);
        } catch (FigureException e) {
            System.out.println("Перехвачена ошибка высоты: " + e.getMessage());
        }

        System.out.println("\nГотово.");
    }
}
