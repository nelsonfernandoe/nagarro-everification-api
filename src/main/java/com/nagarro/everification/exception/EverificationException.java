package com.nagarro.everification.exception;

public class EverificationException extends Exception{
    public EverificationException() {
    }

    public EverificationException(String message) {
        super(message);
    }

    public EverificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EverificationException(Throwable cause) {
        super(cause);
    }

    public EverificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
