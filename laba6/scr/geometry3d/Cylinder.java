package geometry3d;

import geometry2d.Figure;
import exceptions.*;
import java.util.logging.*;

public class Cylinder {
    private static final Logger LOG = Logger.getLogger(Cylinder.class.getName());
    static {
        try {
            LOG.setUseParentHandlers(false);
            if (LOG.getHandlers().length == 0) {
                FileHandler fh = new FileHandler("cylinder.log", true);
                fh.setFormatter(new SimpleFormatter());
                fh.setLevel(Level.ALL);
                LOG.addHandler(fh);
            }
            LOG.setLevel(Level.FINEST);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private final Figure base;
    private final double height;

    public Cylinder(Figure base, double height)
            throws FigureException {
        if (base == null) {
            LOG.severe("Создание цилиндра с null-основанием");
            throw new NullBaseException();
        }
        if (height <= 0) {
            LOG.severe("Создание цилиндра с недопустимой высотой: " + height);
            throw new NonPositiveDimensionException("высота цилиндра", height);
        }
        this.base = base;
        this.height = height;
        LOG.finest("Создан цилиндр: основание=" + base.getClass().getSimpleName() +
                   ", высота=" + height);
    }

    public double Volume() throws FigureException {
        double v = base.Area() * height;
        LOG.finest("Объём цилиндра V=" + v);
        return v;
    }
}
