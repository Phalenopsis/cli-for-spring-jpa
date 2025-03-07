package org.cliforspringjpa.explorer;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.project.ProjectPath;

import java.io.IOException;

public class ProjectExplorer {

    public void explore() throws SpringProjectException {
        try {
            ArchitectureExplorer explorer = new ArchitectureExplorer();
            explorer.explore();
        } catch (SpringProjectException ignored) {

        }
        if(ProjectPath.getInstance().hasArchitecture()) {
            try {
                FileExplorer explorer = new FileExplorer();
                explorer.find();
            } catch (IOException ignored) {

            }
        }
    }
}
