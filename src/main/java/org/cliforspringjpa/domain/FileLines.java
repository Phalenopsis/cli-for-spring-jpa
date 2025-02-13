package org.cliforspringjpa.domain;

import java.util.*;

public class FileLines {
    private final String className;
    private String directoryPath;
    private String packageName;
    private final Set<String> imports = new HashSet<>();
    private List<String> classDeclaration = new ArrayList<>();
    private final HashMap<String, List<String>> attributes = new HashMap<>();
    private final List<String> methods = new ArrayList<>();

    public FileLines(String pClassName) {
        className = pClassName;
    }

    public void setDirectoryPath(String pDirectoryPath) {
        directoryPath = pDirectoryPath;
    }

    public void setPackageName(String pPackageName) {
        if(!pPackageName.endsWith(";")) {
            pPackageName = pPackageName + ";";
        }
        packageName = pPackageName;
    }

    public void addImport(String pImport) {
        imports.add(pImport);
    }

    public void addImports(Collection<String> pImports) {
        imports.addAll(pImports);
    }

    public String getPackageName() {
        return packageName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public List<String> getClassDeclaration() {
        return classDeclaration;
    }

    public HashMap<String, List<String>> getAttributes() {
        return attributes;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void addMethods(List<String> methodsLines) {
        methods.addAll(methodsLines);
    }

    public void addAttribute(String attribute, List<String> lines) {
        attributes.put(attribute, lines);
    }

    public void setClassDeclaration(List<String> classDeclaration) {
        this.classDeclaration = classDeclaration;
    }

    public String getClassName() {
        return className;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
