package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.exception.IdNotFoundException;
import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.project.ProjectPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositoryGenerator extends Generator {

    public RepositoryGenerator(Entity pEntity) {
        super(pEntity);
    }

    @Override
    protected Set<String> generateImports() throws SpringProjectException {
        Set<String> imports = new HashSet<>();
        if(!ProjectPath.getInstance().isEntityArchitecture()) {
            imports.add("import " + getEntityFullyQualifiedClassName() + "." + entity.getName() + ";");
        }
        imports.add("import org.springframework.data.jpa.repository.JpaRepository;");
        return imports;
    }

    @Override
    protected List<String> generateClassDeclaration() {
        List<String> lines = new ArrayList<>();
        String entityName = entity.getName();
        String idType = "Long";
        try {
            idType = entity.getIdType();
        } catch (IdNotFoundException e) {
            System.err.println(e.getMessage());
        }
        String basicLine = "public interface %sRepository extends JpaRepository<%s, %s> {";
        String line = String.format(basicLine, entityName, entityName, idType);
        lines.add(line);
        return lines;
    }

    @Override
    protected void generateClassAttributes() throws SpringProjectException {

    }

    @Override
    protected void generateClassMethods() {

    }
}
