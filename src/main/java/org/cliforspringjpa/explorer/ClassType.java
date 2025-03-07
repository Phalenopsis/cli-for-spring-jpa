package org.cliforspringjpa.explorer;

public enum ClassType {
    MODEl("model"),
    REPOSITORY("repository");
    //add other directories like dto, controller...

    private final String value;

    private ClassType(String pValue) {
        value = pValue;
    }

    public String getValue() {
        return value;
    }
}
