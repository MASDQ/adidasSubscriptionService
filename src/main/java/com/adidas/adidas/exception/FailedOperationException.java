package com.adidas.adidas.exception;

/**
 * Exception in case that an operation fails
 */
public class FailedOperationException extends Exception{
    public FailedOperationException(String message) {
        super(message);
    }
    public FailedOperationException() {}
}
