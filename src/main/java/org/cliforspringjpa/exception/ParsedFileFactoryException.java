package org.cliforspringjpa.exception;

public class ParsedFileFactoryException extends Exception{
    private final String message;

    public ParsedFileFactoryException(String pMessage) {
        message = pMessage;
    }

    @Override
    public String getMessage() {
        String endOfMessage = " is not a recognised argument for ParsedFileFactory";
        return message + endOfMessage;
    }
}
