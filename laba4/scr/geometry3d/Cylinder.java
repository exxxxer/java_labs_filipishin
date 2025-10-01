package geometry3d;

import geometry2d.Figure;

public class Cylinder {
    private Figure base; // основание цилиндра
    private double height;

    public Cylinder(Figure base, double height) {
        this.base = base;
        this.height = height;
    }

    public double Volume() {
        return base.Area() * height;
    }

    public void Show() {
        System.out.println("Цилиндр с основанием:");
        base.Show();
        System.out.println("Высота = " + height + ", объём = " + Volume());
    }
}