package org.cliforspringjpa.cli;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.project.Project;
import org.cliforspringjpa.exception.EndEntityException;
import org.cliforspringjpa.exception.EndOfActionException;
import org.cliforspringjpa.exception.ExitException;
import org.cliforspringjpa.exception.NoScannerException;

public class CLIEntity {
    public void ask(String argument) throws NoScannerException {
        if(argument.isEmpty()) {
            System.out.println("What is your entity's name ?");
            argument = CLIInput.getInstance().askOpenedPascalCaseQuestion();
        }
        Entity entity;
        if(Attribute.getNewTypeList().contains(argument)) {
            entity = Project.getInstance().getEntity(argument);
            System.out.println("Entity " + argument + " already exists. It will be update");
        } else {
            entity = new Entity(argument);
            entity.addAttribute(new Attribute("id", "Long"));
            System.out.println("\tAuto generated id of type Long");
        }
        entity.setModified(true);
        boolean run = true;

        while(run) {
            try {
                CLIAttribute cli = new CLIAttribute(entity);
                Attribute attribute = cli.askForAttribute();
                entity.addAttribute(attribute);
            } catch (ExitException e) {
                throw new RuntimeException(e);
            } catch (EndEntityException | EndOfActionException e) {
                run = false;
                System.out.println("Entity " + entity.getName() + " finished.");
            }

        }
        Project.getInstance().addEntity(entity);
    }
}
