package geometry3d;

import geometry2d.Figure;
import exceptions.FigureException;
import exceptions.NonPositiveDimensionException;
import exceptions.NullBaseException;

public class Cylinder {
    private final Figure base;  // основание
    private final double h;     // высота

    public Cylinder(Figure base, double h) throws FigureException {
        if (base == null) throw new NullBaseException();
        if (h <= 0) throw new NonPositiveDimensionException("Высота", h);
        this.base = base;
        this.h = h;
    }

    public Figure getBase() { return base; }
    public double getH() { return h; }

    public double Volume() { return base.Area() * h; }

    public void Show() {
        System.out.print("Цилиндр { основание = ");
        base.Show();
        System.out.printf("высота=%.3f, объём=%.3f }%n", h, Volume());
    }
}
