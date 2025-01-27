package org.cliforspringjpa.exception;

public class SpringProjectException extends Exception {
    private final String message;

    public SpringProjectException(String pMessage) {
        message = pMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
