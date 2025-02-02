package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.domain.FileLines;
import org.cliforspringjpa.domain.ProjectPath;
import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.utils.CaseManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class Generator {
    protected FileLines fileLines = new FileLines();
    protected Entity entity;
    protected String generatorType;

    public Generator(Entity pEntity) {
        entity = pEntity;
        setGeneratorType();
    }

    public String getDirectoryPath() throws SpringProjectException {
        String directoryPath = "";
        for(char c: getPackageName().toCharArray()) {
            if(c == '.') {
                directoryPath = directoryPath + File.separator;
            } else {
                directoryPath = directoryPath + c;
            }
        }
        return directoryPath;
    }

    public String getPackageName() throws SpringProjectException {
        boolean isEntityArch = ProjectPath.getInstance().isEntityArchitecture();
        String mainPackageName = ProjectPath.getInstance().getPackageName();
        String endTerm = isEntityArch ? ".domain." + CaseManager.switchToKebabCase(entity.getName())
                : "." + CaseManager.switchToKebabCase(generatorType);
        return mainPackageName + endTerm;
    }

    public String getExtendedFileName() {
        return getClassName() + ".java";
    }

    protected void setGeneratorType() {
        String generatorClassName = getClass().getSimpleName();
        generatorType = generatorClassName.replace("Generator", "");
    }

    /**
     * generate class name
     * <ul>eg:
     * <li>Book for EntityGenerator for Book</li>
     * <li>BookRepository for RepositoryGenerator for Book</li>
     * </ul>
     * @return ClassName
     */
    public String getClassName() {
        if(generatorType.equals("Entity")) return entity.getName();
        return entity.getName() + generatorType;
    }

    public String getAbsolutePath() throws SpringProjectException {
        return ProjectPath.getInstance().getJavaPath() + File.separator
                + getDirectoryPath();
    }

    public String getAbsoluteFileName() throws SpringProjectException {
        return getAbsolutePath() + File.separator + getExtendedFileName();
    }

    public void generateLines() throws SpringProjectException {
        fileLines.setPackageName(getPackageName());
        fileLines.addImports(generateImports());
        fileLines.setClassDeclaration(generateClassDeclaration());
        generateClassAttributes();

        //TODO : fill the returned list
    }

    public List<String> getClassDeclarationLines() {
        return fileLines.getClassDeclaration();
    }

    public HashMap<String, List<String>> getAttributes() {
        return fileLines.getAttributes();
    }

    public Set<String> getImports() {
        return fileLines.getImports();
    }

    protected abstract Set<String> generateImports();

    protected abstract List<String> generateClassDeclaration();

    protected abstract void generateClassAttributes() throws SpringProjectException;

    protected abstract void generateClassMethods();
}
