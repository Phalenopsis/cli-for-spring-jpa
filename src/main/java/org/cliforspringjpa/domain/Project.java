package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.generator.EntityGenerator;
import org.cliforspringjpa.generator.Generator;

import java.util.HashMap;
import java.util.Objects;

public class Project {
    private static Project instance;

    private HashMap<String, Generator> generators = new HashMap<>();

    public static Project getInstance() {
        if(Objects.isNull(instance)) {
            instance = new Project();
        }
        return instance;
    }

    private Project() { }

    public void addGenerator(Generator generator) {
        generators.put(generator.getClassName(), generator);
    }

    public Generator getGenerator(String generatorClassName) {
        return generators.get(generatorClassName);
    }

    public String getEntityFileImport(String className) throws SpringProjectException {
        EntityGenerator entityGenerator = (EntityGenerator) generators.get(className);
        return entityGenerator.getPackageName() + "." + className;
    }

    public void reset() {
        generators = new HashMap<>();
    }
}
