package org.cliforspringjpa.domain;

import org.cliforspringjpa.explorer.parsedFile.ParsedFile;

public class ParsedRepository extends ParsedFile {
    public ParsedRepository(String className) {
        super(className);
    }

    @Override
    public void parseLine(String line) {
        if(line.startsWith("package ")) {
            fileLines.setPackageName(line);
            return;
        }
        if(line.startsWith("import ")) {
            fileLines.addImport(line);
            return;
        }
        if (line.startsWith("public")) {
            isInClassDeclaration = true;
        }
        if(isInClassDeclaration) {
            parseClassDeclaration(line);
            return;
        }
        if(line.trim().startsWith("public")) {
            isInMethodDeclaration = true;
        }
        if(isInMethodDeclaration) {
            parseMethod(line);
        }
    }

    @Override
    protected void parseAttributeDeclaration(String line) {

    }
}
