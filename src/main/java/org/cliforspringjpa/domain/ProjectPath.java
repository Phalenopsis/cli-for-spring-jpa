package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.explorer.PomXmlExplorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ProjectPath {
    private static ProjectPath instance;

    private final String userPath;
    private final String packagePath;
    private final String packageName;
    private boolean isEntityArchitecture;

    public static ProjectPath getInstance() throws SpringProjectException {
        if(Objects.isNull(instance)) {
            PackageName packageName = PomXmlExplorer.getInstance().getProjectPackage();
            instance = new ProjectPath(packageName);
        }
        return instance;
    }

    private ProjectPath(PackageName pPackageName) {
        packageName = pPackageName.getPackageName();
        userPath = System.getProperty("user.dir");
        packagePath = getAbsoluteSrcPath() + File.separator + "main" +
                File.separator + "java" + File.separator + getDirectoryPath(packageName);
    }

    public String getAbsoluteSrcPath() {
        return userPath + File.separator + "src";
    }

    public String getAbsoluteMainPackagePath() {
        return packagePath;
    }

    public boolean isEntityArchitecture() {
        return isEntityArchitecture;
    }

    public void setEntityArchitecture(boolean entityArchitecture) {
        isEntityArchitecture = entityArchitecture;
        if(isEntityArchitecture) {
            try {
                Files.createDirectory(Paths.get(packagePath + File.separator + "domain"));
            } catch (FileAlreadyExistsException ignored) {
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public String getJavaPath() {
        return getAbsoluteSrcPath() + File.separator + "main" + File.separator + "java";
    }

    public String getDirectoryPath(String packageName) {
        String directoryPath = "";
        for(char c: packageName.toCharArray()) {
            if(c == '.') {
                directoryPath = directoryPath + File.separator;
            } else {
                directoryPath = directoryPath + c;
            }
        }
        return directoryPath;
    }
}
