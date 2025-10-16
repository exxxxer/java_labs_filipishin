package test;

import geometry2d.*;
import geometry3d.*;
import exceptions.*;
import java.util.logging.*;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    static {
        LOG.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new SimpleFormatter());
        ch.setLevel(Level.ALL);
        LOG.addHandler(ch);
        LOG.setLevel(Level.FINE);
    }

    public static void main(String[] args) {
        LOG.fine("Старт программы");
        try {
            Figure c = new Circle(3);
            Figure r = new Rectangle(4, 5);

            c.Show();
            r.Show();
            System.out.println("S круга = " + c.Area());
            System.out.println("S прямоугольника = " + r.Area());

            Cylinder z = new Cylinder(c, 10);
            System.out.println("V цилиндра = " + z.Volume());
        } catch (FigureException ex) {
            LOG.log(Level.SEVERE, "Ошибка работы с фигурами/цилиндром: " + ex.getMessage(), ex);
            System.out.println("Ошибка: " + ex.getMessage());
        }

        try { new Circle(0); }
        catch (FigureException e) { System.out.println("Ожидаемая ошибка (круг): " + e.getMessage()); }

        try { new Rectangle(-2, 3); }
        catch (FigureException e) { System.out.println("Ожидаемая ошибка (прямоугольник): " + e.getMessage()); }

        try { new Cylinder(null, 5); }
        catch (FigureException e) { System.out.println("Ожидаемая ошибка (цилиндр): " + e.getMessage()); }

        LOG.fine("Завершение программы");
    }
}
