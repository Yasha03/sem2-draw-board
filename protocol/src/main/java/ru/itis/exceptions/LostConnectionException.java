package ru.itis.exceptions;

public class LostConnectionException extends Exception{
    public LostConnectionException() {
        super();
    }

    public LostConnectionException(String message) {
        super(message);
    }

    public LostConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
