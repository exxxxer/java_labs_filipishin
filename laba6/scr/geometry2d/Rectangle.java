package geometry2d;

import exceptions.*;
import java.util.logging.*;

public class Rectangle implements Figure {
    private static final Logger LOG = Logger.getLogger(Rectangle.class.getName());
    static {
        try {
            LOG.setUseParentHandlers(false);
            if (LOG.getHandlers().length == 0) {
                FileHandler fh = new FileHandler("figures.log", true);
                fh.setFormatter(new XMLFormatter());
                fh.setLevel(Level.ALL);
                LOG.addHandler(fh);
            }
            LOG.setLevel(Level.INFO);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private final double w, h;

    public Rectangle(double w, double h) throws NonPositiveDimensionException {
        if (w <= 0) { LOG.info("w=" + w + " (ошибка)"); throw new NonPositiveDimensionException("ширина", w); }
        if (h <= 0) { LOG.info("h=" + h + " (ошибка)"); throw new NonPositiveDimensionException("высота", h); }
        this.w = w; this.h = h;
        LOG.info("Создан прямоугольник w=" + w + ", h=" + h);
    }

    @Override
    public double Area() {
        double s = w * h;
        LOG.info("Площадь прямоугольника w=" + w + ", h=" + h + " S=" + s);
        return s;
    }

    @Override
    public void Show() {
        System.out.println("Прямоугольник: w=" + w + ", h=" + h);
        LOG.info("Вызван Show() у прямоугольника w=" + w + ", h=" + h);
    }
}
