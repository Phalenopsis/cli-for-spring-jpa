package org.cliforspringjpa.cli;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.exception.EndOfActionException;
import org.cliforspringjpa.exception.ExitException;
import org.cliforspringjpa.exception.NoScannerException;
import org.cliforspringjpa.generator.RepositoryGenerator;
import org.cliforspringjpa.project.Project;

public class CLIRepository {
    public void ask(String argument) throws NoScannerException, EndOfActionException, ExitException {
        Project project = Project.getInstance();
        if(argument.isEmpty()) {
            System.out.println("What is your entity's name ?");
            argument = CLIInput.getInstance().askOpenedPascalCaseQuestion();
        }
        if(Attribute.getNewTypeList().contains(argument)) {
            if(project.getRepositories().contains(argument)) {
                System.out.println(argument + "Repository already exists");
            } else {
                Entity entity = project.getEntity(argument);
                System.out.println(entity.getName() + "Repository will be created");
                project.addGenerator(new RepositoryGenerator(entity));
            }

        } else {
            System.out.println(argument + " is not a recognized entity");
            if(project.getEntitiesList().isEmpty()) {
                System.out.println("There's no entity in project. Please make one before trying making repository");
                throw new EndOfActionException();
            }
            System.out.println("For memory, projects entities are :");
            for(String projectEntity : project.getEntitiesList()) {
                System.out.println("\t" + projectEntity);
            }
            ask("");
        }
    }
}
