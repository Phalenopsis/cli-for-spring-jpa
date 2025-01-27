package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.explorer.PomXmlExplorer;

import java.io.File;
import java.util.Objects;

public class Project {
    private static Project instance;

    private final String userPath;
    private final String packagePath;
    private final PackageName packageName;

    public static Project getInstance() throws SpringProjectException {
        if(Objects.isNull(instance)) {
            String packageNameString = PomXmlExplorer.getInstance().getProjectPackage();
            instance = new Project(packageNameString);
        }
        return instance;
    }

    private Project(String pPackageName) {
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
}
