package org.cliforspringjpa.exception;

import org.cliforspringjpa.domain.Entity;

public class IdNotFoundException extends Exception {
    private final String message;

    public IdNotFoundException(String entityName) {
        message = entityName + ": id not found. Repository generated with Long id type. Please check your "
                + entityName + "repository when generated.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
