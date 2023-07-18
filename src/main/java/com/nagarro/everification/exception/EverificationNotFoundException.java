package com.nagarro.everification.exception;

public class EverificationNotFoundException extends Exception{
    public EverificationNotFoundException() {
    }

    public EverificationNotFoundException(String message) {
        super(message);
    }

    public EverificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EverificationNotFoundException(Throwable cause) {
        super(cause);
    }

    public EverificationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
