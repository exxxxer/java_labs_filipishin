package exceptions;

public class NullBaseException extends FigureException {
    public NullBaseException() {
        super("Основание цилиндра не должно быть null");
    }
}