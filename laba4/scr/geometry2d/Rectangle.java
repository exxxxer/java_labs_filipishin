package geometry2d;

public class Rectangle implements Figure {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double Area() {
        return width * height;
    }

    @Override
    public void Show() {
        System.out.println("Прямоугольник " + width + " x " + height + ", площадь = " + Area());
    }
}