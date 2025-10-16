package geometry2d;

import exceptions.*;
import java.util.logging.*;

public class Circle implements Figure {
    private static final Logger LOG = Logger.getLogger(Circle.class.getName());
    static {
        try {
            LOG.setUseParentHandlers(false);
            if (LOG.getHandlers().length == 0) {
                FileHandler fh = new FileHandler("figures.log", true);
                fh.setFormatter(new XMLFormatter());
                fh.setLevel(Level.ALL);
                LOG.addHandler(fh);
            }
            LOG.setLevel(Level.SEVERE);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private final double r;

    public Circle(double r) throws NonPositiveDimensionException {
        if (r <= 0) {
            LOG.severe("Попытка создать круг с r=" + r);
            throw new NonPositiveDimensionException("радиус", r);
        }
        this.r = r;
        LOG.severe("Создан круг r=" + r);
    }

    @Override
    public double Area() {
        double s = Math.PI * r * r;
        LOG.severe("Площадь круга r=" + r + " S=" + s);
        return s;
    }

    @Override
    public void Show() {
        System.out.println("Круг: r=" + r);
        LOG.severe("Вызван Show() у круга r=" + r);
    }
}
