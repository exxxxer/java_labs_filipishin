package exceptions;

public class NonPositiveDimensionException extends FigureException {
    public NonPositiveDimensionException(String what, double value) {
        super(what + " должен быть > 0, получено: " + value);
    }
}