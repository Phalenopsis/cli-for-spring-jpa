package org.cliforspringjpa.exception;

public class NoScannerException extends Exception{
    private final String message;

    public NoScannerException(String pMessage) {
        message = pMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
