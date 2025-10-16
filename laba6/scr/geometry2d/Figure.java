package geometry2d;

import exceptions.FigureException;

public interface Figure {
    double Area() throws FigureException;
    void Show();
}
