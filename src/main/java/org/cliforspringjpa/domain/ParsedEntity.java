package org.cliforspringjpa.domain;

import org.cliforspringjpa.explorer.parsedFile.ParsedFile;

public class ParsedEntity extends ParsedFile {
    private final Entity entity;

    public ParsedEntity(String className) {
        super(className);
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

    @Override
    protected void parseAttributeDeclaration(String line) {
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
}
