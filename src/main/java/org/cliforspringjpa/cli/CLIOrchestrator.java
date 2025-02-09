package org.cliforspringjpa.cli;

import org.cliforspringjpa.domain.InputOrder;
import org.cliforspringjpa.domain.ProjectPath;
import org.cliforspringjpa.exception.EndOfActionException;
import org.cliforspringjpa.exception.ExitException;
import org.cliforspringjpa.exception.NoScannerException;
import org.cliforspringjpa.exception.SpringProjectException;

import java.util.*;

public class CLIOrchestrator {
    private static CLIOrchestrator instance;
    private final Scanner scanner;

    private static final String NICO_MAKE_ENTITY = "nico make entity";

    public static final Set<String> ORDERS = Set.of(NICO_MAKE_ENTITY);

    public static CLIOrchestrator getInstance(Scanner scanner) {
        if (Objects.isNull(instance)) {
            instance = new CLIOrchestrator(scanner);
        }
        return instance;
    }

    public static CLIOrchestrator getInstance() throws NoScannerException {
        if (Objects.isNull(instance)) {
            throw new NoScannerException("No scanner");
        }
        return instance;
    }

    private CLIOrchestrator(Scanner pScanner) {
        scanner = pScanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void run() throws SpringProjectException, NoScannerException, ExitException {
        explain();
        askArchitecture();
        askMain();

    }

    public void explain() {
        System.out.println(red(bold("Welcome on SpringCLI for CRUD")));
        System.out.println("At any moment you could hint \"\\help\".");
        System.out.println("FirstStep : choose your architecture :");
    }

    private String bold(String term) {
        return reset(ConstantConsoleColor.ANSI_BOLD + term);
    }

    private String red(String term) {
        return reset(ConstantConsoleColor.ANSI_RED + term);
    }

    private String reset(String term) {
        return term + ConstantConsoleColor.ANSI_RESET;
    }

    private void askArchitecture() throws SpringProjectException {
        ArchitectureCLI cli = new ArchitectureCLI(scanner);
        boolean isEntityArchitecture = cli.askArchitecture();
        ProjectPath.getInstance().setEntityArchitecture(isEntityArchitecture);
        System.out.println("You choose " + (isEntityArchitecture ? "Entity" : "Controller") + " architecture");
    }

    private void askMain() throws NoScannerException, ExitException{
        boolean run = true;

        while(run) {
            try {
                String order = CLIInput.getInstance().askOpenedQuestionWithLimitedChoicesAndPossibleArgument(ORDERS);
                InputOrder input = new InputOrder(order);

                switch (input.getOrder()) {
                    case NICO_MAKE_ENTITY:
                        askCLIEntity(input.getArgument());
                        break;
                    default:
                        run = false;
                }
            } catch (EndOfActionException e){
                run = false;
            }
        }
    }

    private void askCLIEntity(String argument) throws NoScannerException {
        CLIEntity cli = new CLIEntity();
        cli.ask(argument);
    }
}
