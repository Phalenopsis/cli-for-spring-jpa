package org.cliforspringjpa.explorer;

import org.cliforspringjpa.project.ProjectPath;
import org.cliforspringjpa.exception.SpringProjectException;

import java.io.File;
import java.util.Objects;

public class SpringProjectValidator {
    private static SpringProjectValidator instance;

    public static SpringProjectValidator getInstance() {
        if (Objects.isNull(instance)) {
            return new SpringProjectValidator();
        }
        return instance;
    }

    private SpringProjectValidator() {

    }

    public void verifyProject() throws SpringProjectException {
        PomXmlExplorer.getInstance().verifyPomXml();
        verifySrcDir();
        verifyPackageDir();
    }

    private void verifySrcDir() throws SpringProjectException {
        String srcDirPath = ProjectPath.getInstance().getAbsoluteSrcPath();
        File srcDirFile = new File(srcDirPath);
        if(!(srcDirFile.exists() && srcDirFile.isDirectory())) {
            throw new SpringProjectException("src directory does not exists.");
        }
    }

    private void verifyPackageDir() throws SpringProjectException {
        String packageDirPath = ProjectPath.getInstance().getAbsoluteMainPackagePath();
        File srcDirFile = new File(packageDirPath);
        if(!(srcDirFile.exists() && srcDirFile.isDirectory())) {
            throw new SpringProjectException("package " + packageDirPath + " directory does not exists.");
        }
    }
}
