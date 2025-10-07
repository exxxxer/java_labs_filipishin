package geometry2d;

import exceptions.NonPositiveDimensionException;

public class Rectangle implements Figure {
    private final double a;
    private final double b;

    public Rectangle(double a, double b) throws NonPositiveDimensionException {
        if (a <= 0) throw new NonPositiveDimensionException("Сторона a", a);
        if (b <= 0) throw new NonPositiveDimensionException("Сторона b", b);
        this.a = a;
        this.b = b;
    }

    public double getA() { return a; }
    public double getB() { return b; }

    @Override
    public double Area() { return a * b; }

    @Override
    public void Show() {
        System.out.printf("Прямоугольник (a=%.3f, b=%.3f), площадь=%.3f%n", a, b, Area());
    }
}
