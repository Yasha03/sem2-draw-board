package ru.itis.exceptions;

public class ProtocolHeaderException extends Exception{
    public ProtocolHeaderException() {
        super();
    }

    public ProtocolHeaderException(String message) {
        super(message);
    }

    public ProtocolHeaderException(String message, Throwable cause) {
        super(message, cause);
    }
}