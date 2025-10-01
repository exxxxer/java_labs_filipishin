package test;

import geometry2d.*;
import geometry3d.*;

public class Main {
    public static void main(String[] args) {
        // Тестируем фигуры
        Figure c = new Circle(5);
        Figure r = new Rectangle(4, 6);

        c.Show();
        r.Show();

        // Цилиндр с основанием-кругом
        Cylinder cyl1 = new Cylinder(c, 10);
        cyl1.Show();

        // Цилиндр с основанием-прямоугольником
        Cylinder cyl2 = new Cylinder(r, 8);
        cyl2.Show();
    }
}