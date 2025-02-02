package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.domain.Project;
import org.cliforspringjpa.domain.Relationship;
import org.cliforspringjpa.exception.SpringProjectException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityGenerator extends Generator{

    public EntityGenerator(Entity pEntity) {
        super(pEntity);
    }

    @Override
    protected Set<String> generateImports() {
        Set<String> list = new HashSet<>();
        list.add("import jakarta.persistence.*;");
        return list;
    }

    @Override
    protected List<String> generateClassDeclaration() {
        List<String> lines = new ArrayList<>();
        lines.add("@Entity");
        lines.add("public class " + entity.getName() + " {");
        return lines;
    }

    @Override
    protected void generateClassAttributes() throws SpringProjectException {
        EntityAttributeLineGenerator attributeLineGenerator = new EntityAttributeLineGenerator(entity);
        for(Attribute attribute: entity.getSet()) {
            if(attribute.getRelationship() != Relationship.NO_RELATION)
                fileLines.addImport(getImport(attribute));
            fileLines.addAttribute(attribute.getName(), attributeLineGenerator.generateAttribute(attribute));
        }
    }

    private String getImport(Attribute attribute) throws SpringProjectException {
        Project project = Project.getInstance();
        String className = attribute.getType();

        return project.getEntityFileImport(className);
    }

    @Override
    protected void generateClassMethods() {

    }



}
