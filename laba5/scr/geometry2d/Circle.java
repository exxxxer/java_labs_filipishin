package geometry2d;

import exceptions.NonPositiveDimensionException;

public class Circle implements Figure {
    private final double r;

    public Circle(double r) throws NonPositiveDimensionException {
        if (r <= 0) throw new NonPositiveDimensionException("Радиус", r);
        this.r = r;
    }

    public double getR() { return r; }

    @Override
    public double Area() { return Math.PI * r * r; }

    @Override
    public void Show() {
        System.out.printf("Окружность (r=%.3f), площадь=%.3f%n", r, Area());
    }
}