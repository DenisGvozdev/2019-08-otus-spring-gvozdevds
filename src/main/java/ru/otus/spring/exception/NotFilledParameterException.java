package ru.otus.spring.exception;

public class NotFilledParameterException extends Exception {

    public NotFilledParameterException() {
        super();
    }

    public NotFilledParameterException(String message) {
        super(message);
    }

    public NotFilledParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFilledParameterException(Throwable cause) {
        super(cause);
    }
}
