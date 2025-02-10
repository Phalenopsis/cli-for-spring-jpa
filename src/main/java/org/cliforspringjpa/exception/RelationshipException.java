package org.cliforspringjpa.exception;

public class RelationshipException extends Exception{
    String message;

    public RelationshipException(String pMessage) {
        message = pMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
