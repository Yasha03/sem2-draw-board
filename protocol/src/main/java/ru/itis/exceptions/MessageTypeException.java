package ru.itis.exceptions;

public class MessageTypeException extends Exception{
    public MessageTypeException() {
        super();
    }

    public MessageTypeException(String message) {
        super(message);
    }

    public MessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}