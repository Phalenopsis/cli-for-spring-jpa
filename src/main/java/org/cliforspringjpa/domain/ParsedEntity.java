package org.cliforspringjpa.domain;

import java.util.ArrayList;
import java.util.List;

public class ParsedEntity {
    private final FileLines fileLines;
    private boolean isInClassDeclaration = false;
    private boolean isInAttributeDeclaration = false;
    private boolean isInMethodDeclaration = false;

    private final Entity entity;

    private final List<String> classDeclaration = new ArrayList<>();
    private List<String> actualAttributeDeclaration = new ArrayList<>();
    private String actualAttribute;
    private List<String> methods = new ArrayList<>();
    private int blockCounter = 0;

    public ParsedEntity(String className) {
        fileLines = new FileLines(className);
        entity = new Entity(className);
    }

    public Entity getEntity() {
        return entity;
    }

    public void parseLine(String line) {
        if(line.startsWith("package ")) {
            fileLines.setPackageName(line);
            return;
        }
        if(line.startsWith("import ")) {
            fileLines.addImport(line);
            return;
        }
        if (line.startsWith("@Entity")) {
            isInClassDeclaration = true;
        }
        if(isInClassDeclaration) {
           parseClassDeclaration(line);
           return;
        }
        if(!isInAttributeDeclaration && line.trim().startsWith("@")) {
            isInAttributeDeclaration = true;
        }
        if(isInAttributeDeclaration) {
            parseAttributeDeclaration(line);
            return;
        }

        if ((line.trim().startsWith("public")
                || line.trim().startsWith("private")
                || line.trim().startsWith("protected")
                ) && line.contains("(")
                && line.contains(")")
                && line.contains("{")
        ){
            isInMethodDeclaration = true;
        }
        if(isInMethodDeclaration) {
            parseMethod(line);
        }
    }

    private void parseAttributeDeclaration(String line) {
        actualAttributeDeclaration.add(line);
        String trimmedLine = line.trim();
        if ((trimmedLine.startsWith("private")
                || trimmedLine.startsWith("protected")
                || trimmedLine.startsWith("public"))
                && trimmedLine.endsWith(";")
        ) {
            String[] lineArray = trimmedLine.split(" ");
            String attributeType = lineArray[1];
            String lastTerm =  lineArray[2];

            actualAttribute = lastTerm.substring(0, lastTerm.length() - 1);
            Attribute attribute = new Attribute(actualAttribute, attributeType);
            attribute.setDone(true);
            entity.addAttribute(attribute);
        }
        if(line.endsWith(";")) {
            isInAttributeDeclaration = false;
            fileLines.addAttribute(actualAttribute, actualAttributeDeclaration);
            resetActualAttribute();
        }
    }

    private void resetActualAttribute() {
        actualAttribute = null;
        actualAttributeDeclaration = new ArrayList<>();
    }

    public void parseClassDeclaration(String line) {
        classDeclaration.add(line);
            if( line.contains("{")) {
            isInClassDeclaration = false;
            fileLines.setClassDeclaration(classDeclaration);
        }
    }

    private void parseMethod(String line) {
        methods.add(line);
        if(line.contains("{")) blockCounter += 1;
        if(line.contains("}")) blockCounter -= 1;
        if(line.contains("}") && blockCounter == 0) {
            methods.add("");
            isInMethodDeclaration = false;
            fileLines.addMethods(methods);
            methods = new ArrayList<>();
        }
    }

    public FileLines getFileLines() {
        return fileLines;
    }
}
