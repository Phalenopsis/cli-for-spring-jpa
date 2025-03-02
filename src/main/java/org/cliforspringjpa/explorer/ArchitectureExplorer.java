package org.cliforspringjpa.explorer;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.project.ProjectPath;

import java.io.File;

public class ArchitectureExplorer {
    public void explore() throws SpringProjectException {
        String path = ProjectPath.getInstance().getAbsoluteMainPackagePath();

        File domainDirFile = new File(path + File.separator + "domain");
        if (domainDirFile.exists() && domainDirFile.isDirectory()) {
            ProjectPath.getInstance().setEntityArchitecture(true);
            return;
        }
        File modelDirFile = new File(path + File.separator + "model");
        if(modelDirFile.exists() && domainDirFile.isDirectory()) {
            ProjectPath.getInstance().setEntityArchitecture(false);
        }
    }
}
