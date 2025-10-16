package exceptions;

public class NonPositiveDimensionException extends FigureException {
    public NonPositiveDimensionException(String what, double value) {
        super("Недопустимое значение \"" + what + "\": " + value + " (должно быть > 0)");
    }
}
