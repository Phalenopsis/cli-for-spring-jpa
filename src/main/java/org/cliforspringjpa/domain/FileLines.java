package org.cliforspringjpa.domain;

import java.util.*;

public class FileLines {
    private String packageName;
    private Set<String> imports = new HashSet<>();
    private List<String> classDeclaration = new ArrayList<>();
    private HashMap<String, List<String>> attributes = new HashMap<>();
    private List<String> methods = new ArrayList<>();

    public FileLines() {

    }

    public void setPackageName(String pPackageName) {
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
}
