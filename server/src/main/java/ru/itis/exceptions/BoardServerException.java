package ru.itis.exceptions;

public class BoardServerException extends RuntimeException{
    public BoardServerException() {
        super();
    }

    public BoardServerException(String message) {
        super(message);
    }

    public BoardServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
