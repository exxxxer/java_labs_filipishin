package exceptions;

public class NullBaseException extends FigureException {
    public NullBaseException() {
        super("Основание цилиндра (Figure) не задано (null)");
    }
}
