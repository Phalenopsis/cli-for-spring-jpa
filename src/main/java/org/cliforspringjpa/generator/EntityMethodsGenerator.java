package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.domain.Relationship;
import org.cliforspringjpa.utils.CaseManager;

import java.util.ArrayList;
import java.util.List;

public class EntityMethodsGenerator {
    private Entity entity;

    EntityMethodsGenerator(Entity pEntity) {
        entity = pEntity;
    }

    public List<String> generateGettersAndSetters(){
        List<String> methodsLines = new ArrayList<>();
        for(Attribute attribute: entity.getSet()) {
            if(attribute.getRelationship().equals(Relationship.ONE_TO_MANY)
            || attribute.getRelationship().equals(Relationship.MANY_TO_MANY)) {
                methodsLines.addAll(generateManyGettersAndSetters(attribute));
            } else {
                methodsLines.addAll(generateSimpleGettersAndSetters(attribute));
            }
        }
        return methodsLines;
    }

    private List<String> generateManyGettersAndSetters(Attribute attribute) {
        List<String> methodsLines = generateManyGetter(attribute);
        methodsLines.add("");
        methodsLines.addAll(generateManySetter(attribute));
        methodsLines.add("");
        return methodsLines;
    }

    private List<String> generateManyGetter(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add(
                "\tpublic List<"
                        + attribute.getType()
                        + "> get" + CaseManager.switchToPascalCase(attribute.getName())
                        + "s() {");
        lines.add("\t\treturn " + attribute.getName() + "s;");
        lines.add("\t}");

        return lines;
    }

    private List<String> generateManySetter(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\tpublic void"
                + " set" + CaseManager.switchToPascalCase(attribute.getName())
                + "s(List<"
                + attribute.getType()
                + "> "
                + attribute.getName()
                + "List) {");
        lines.add("\t\t" + attribute.getName() + "s = "
                + attribute.getName()
                + "List;");
        lines.add("\t}");
        return lines;
    }

    private List<String> generateSimpleGettersAndSetters(Attribute attribute) {
        List<String> methodsLines = generateGetter(attribute);
        methodsLines.add("");
        methodsLines.addAll(generateSetter(attribute));
        methodsLines.add("");
        return methodsLines;
    }

    private List<String> generateGetter(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add(
                "\tpublic "
                        + attribute.getType()
                        + " get" + CaseManager.switchToPascalCase(attribute.getName())
                        + "() {");
        lines.add("\t\treturn " + attribute.getName() + ";");
        lines.add("\t}");

        return lines;
    }

    private List<String> generateSetter(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\tpublic void"
                + " set" + CaseManager.switchToPascalCase(attribute.getName())
                + "("
                + attribute.getType()
                + " p"
                + CaseManager.switchToPascalCase(attribute.getName())
                + ") {");
        lines.add("\t\t" + attribute.getName() + " = p"
                + CaseManager.switchToPascalCase(attribute.getName())
                + ";");
        lines.add("\t}");
        return lines;
    }
}
