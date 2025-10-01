package geometry2d;

public class Circle implements Figure {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double Area() {
        return Math.PI * radius * radius;
    }

    @Override
    public void Show() {
        System.out.println("Круг с радиусом " + radius + ", площадь = " + Area());
    }
}