package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.explorer.PomXmlExplorer;

import java.io.File;
import java.util.Objects;

public class ProjectPath {
    private static ProjectPath instance;

    private final String userPath;
    private final String packagePath;
    private final PackageName packageName;
    private boolean isEntityArchitecture;

    public static ProjectPath getInstance() throws SpringProjectException {
        if(Objects.isNull(instance)) {
            String packageNameString = PomXmlExplorer.getInstance().getProjectPackage();
            instance = new ProjectPath(packageNameString);
        }
        return instance;
    }

    private ProjectPath(String pPackageName) {
        packageName = new PackageName(pPackageName);
        userPath = System.getProperty("user.dir");
        packagePath = getAbsoluteSrcPath() + File.separator + "main" +
                File.separator + "java" + File.separator + packageName.getPath();
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
    }

    public String getPackageName() {
        return packageName.getCompleteName();
    }

    public String getJavaPath() {
        return getAbsoluteSrcPath() + File.separator + "java";
    }
}
