package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.filecreator.FileCreator;
import org.cliforspringjpa.generator.EntityGenerator;
import org.cliforspringjpa.generator.Generator;

import java.io.IOException;
import java.util.*;

public class Project {
    private static Project instance;

    private HashMap<String, Generator> generators = new HashMap<>();
    private HashMap<String, Entity> entities = new HashMap<>();
    private final Set<String> BASIC_TYPES = Set.of("String", "Long", "double", "int");

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
        entities = new HashMap<>();
        generators = new HashMap<>();
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getName(), entity);
    }

    private void generateGenerators() {
        for(Entity entity: entities.values()) {
            EntityGenerator generator = new EntityGenerator(entity);
            addGenerator(generator);
        }
    }

    public Set<String> getEntitiesList() {
        return entities.keySet();
    }

    public Set<String> getBASIC_TYPES() {
        return BASIC_TYPES;
    }

    public Entity getEntity(String entityName) {
        return entities.get(entityName);
    }

    public void generateFile() throws IOException, SpringProjectException {
        generateGenerators();
        for(Generator generator: generators.values()) {
            generator.generateLines();
        }

        for(Generator generator: generators.values()) {
            FileCreator creator = new FileCreator(generator.getFileLines());
            creator.createDirectory();
            creator.createFile();
        }
    }
}
