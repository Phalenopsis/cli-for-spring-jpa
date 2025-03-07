package org.cliforspringjpa.explorer.parsedFile;

import org.cliforspringjpa.domain.FileLines;

import java.util.ArrayList;
import java.util.List;

public abstract class ParsedFile {
    protected final FileLines fileLines;
    protected boolean isInClassDeclaration = false;
    protected boolean isInAttributeDeclaration = false;
    protected boolean isInMethodDeclaration = false;

    protected final List<String> classDeclaration = new ArrayList<>();
    protected List<String> actualAttributeDeclaration = new ArrayList<>();
    protected String actualAttribute;
    protected List<String> methods = new ArrayList<>();
    protected int blockCounter = 0;

    public ParsedFile(String className) {
        fileLines = new FileLines(className);
    }

    abstract public void parseLine(String line);

    abstract protected void parseAttributeDeclaration(String line);

    protected void resetActualAttribute() {
        actualAttribute = null;
        actualAttributeDeclaration = new ArrayList<>();
    }

    protected void parseClassDeclaration(String line) {
        classDeclaration.add(line);
        if( line.contains("{")) {
            isInClassDeclaration = false;
            fileLines.setClassDeclaration(classDeclaration);
        }
    }

    protected void parseMethod(String line) {
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
