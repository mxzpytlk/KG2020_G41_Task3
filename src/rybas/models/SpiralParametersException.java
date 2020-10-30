package rybas.models;

public class SpiralParametersException extends Exception{
    public SpiralParametersException() {
        super();
    }

    public SpiralParametersException(String message) {
        super(message);
    }

    public SpiralParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpiralParametersException(Throwable cause) {
        super(cause);
    }

    protected SpiralParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
