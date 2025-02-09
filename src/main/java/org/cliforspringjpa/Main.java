package org.cliforspringjpa;

import org.cliforspringjpa.cli.CLIOrchestrator;
import org.cliforspringjpa.exception.ExitException;
import org.cliforspringjpa.exception.NoScannerException;
import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.explorer.SpringProjectValidator;

import java.util.Scanner;

public class        Main {
    public static void main(String[] args) {
        boolean runing = true;
        try {
            SpringProjectValidator.getInstance().verifyProject();
        } catch (SpringProjectException e) {
            runing = false;
            System.err.println(e.getMessage());
            System.err.println("CLI can't work, it isn't a Spring project");
        }

        if(runing) {
            try(Scanner scanner = new Scanner(System.in)) {
                CLIOrchestrator.getInstance(scanner).run();
            } catch (SpringProjectException | NoScannerException e) {
                throw new RuntimeException(e);
            } catch (ExitException e) {
                System.out.println("Good bye");
            }
        }
    }
}