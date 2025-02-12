package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.project.ProjectPath;
import org.cliforspringjpa.exception.SpringProjectException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.util.List;
import java.util.Set;

public class TestGenerator {
    class RepositoryGenerator extends Generator {
        RepositoryGenerator(Entity pEntity) {
            super(pEntity);
        }

        @Override
        protected Set<String> generateImports() {
            return Set.of();
        }

        @Override
        protected List<String> generateClassDeclaration() {
            return List.of();
        }

        @Override
        protected void generateClassAttributes() {
        }

        @Override
        protected void generateClassMethods() { }

    }

    @Test
    public void testGetPackageName_EntityArchitecture() throws SpringProjectException {
        Entity entity = new Entity("Book");
        ProjectPath.getInstance().setEntityArchitecture(true);
        RepositoryGenerator generator = new RepositoryGenerator(entity);

        String packageName = generator.getPackageName();

        Assertions.assertEquals("org.cliforspringjpa.domain.book", packageName);
    }

    @Test
    public void testGetPackageName_ControllerArchitecture() throws SpringProjectException {
        Entity entity = new Entity("Book");
        ProjectPath.getInstance().setEntityArchitecture(false);
        RepositoryGenerator generator = new RepositoryGenerator(entity);

        String packageName = generator.getPackageName();

        Assertions.assertEquals("org.cliforspringjpa.repository", packageName);
    }

    @Test
    public void testGetExtendedFileName() {
        Entity entity = new Entity("Book");
        RepositoryGenerator generator = new RepositoryGenerator(entity);

        String packageName = generator.getExtendedFileName();

        Assertions.assertEquals("BookRepository.java", packageName);
    }

    @Test
    public void testGetDirectoryPath_EntityArchitecture() throws SpringProjectException {
        Entity entity = new Entity("Book");
        ProjectPath.getInstance().setEntityArchitecture(true);
        RepositoryGenerator generator = new RepositoryGenerator(entity);

        String path = generator.getDirectoryPath();

        Assertions.assertEquals(
                "org" + File.separator +
                        "cliforspringjpa" + File.separator +
                        "domain" + File.separator +
                        "book", path);
    }

    @Test
    public void testGetDirectoryPath_ControllerArchitecture() throws SpringProjectException {
        Entity entity = new Entity("Book");
        ProjectPath.getInstance().setEntityArchitecture(false);
        RepositoryGenerator generator = new RepositoryGenerator(entity);

        String path = generator.getDirectoryPath();

        Assertions.assertEquals(
                "org" + File.separator +
                        "cliforspringjpa" + File.separator +
                        "repository", path);
    }

    @Test
    public void testGetClassName_EntityGenerator() {
        Entity entity = new Entity("Book");
        EntityGenerator generator = new EntityGenerator(entity);

        String name = generator.getClassName();

        Assertions.assertEquals("Book", name);
    }

    @Test
    public void testGetClassName_RepositoryGenerator() {
        Entity entity = new Entity("Book");
        RepositoryGenerator generator = new RepositoryGenerator(entity);

        String name = generator.getClassName();

        Assertions.assertEquals("BookRepository", name);
    }
}
