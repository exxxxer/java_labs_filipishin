package exceptions;

public class FigureException extends Exception {
    public FigureException(String message) { super(message); }
    public FigureException(String message, Throwable cause) { super(message, cause); }
}
