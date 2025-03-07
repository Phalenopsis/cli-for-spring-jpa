package org.cliforspringjpa.explorer.parsedFile;

import org.cliforspringjpa.domain.ParsedEntity;
import org.cliforspringjpa.domain.ParsedRepository;
import org.cliforspringjpa.exception.ParsedFileFactoryException;
import org.cliforspringjpa.explorer.ClassType;

public class ParsedFileFactory {
    public static ParsedFile build(String className, ClassType type) throws ParsedFileFactoryException {

        switch (type) {
            case MODEl -> {
                return new  ParsedEntity(className);
            }
            case REPOSITORY -> {
                return new ParsedRepository(className);
            }
            default -> throw new ParsedFileFactoryException(type.getValue());
        }
    }
}
