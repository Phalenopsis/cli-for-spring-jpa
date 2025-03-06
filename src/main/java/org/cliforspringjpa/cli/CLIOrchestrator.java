package org.cliforspringjpa.cli;

import org.cliforspringjpa.domain.InputOrder;
import org.cliforspringjpa.project.ProjectPath;
import org.cliforspringjpa.exception.EndOfActionException;
import org.cliforspringjpa.exception.ExitException;
import org.cliforspringjpa.exception.NoScannerException;
import org.cliforspringjpa.exception.SpringProjectException;

import java.util.*;

public class CLIOrchestrator {
    private static final String NICO_MAKE_ENTITY = "nico make entity";
    private static final String NICO_MAKE_REPOSITORY = "nico make repository";
    private static final String HELP = "/help";
    public static final Set<String> ORDERS = Set.of(NICO_MAKE_ENTITY, NICO_MAKE_REPOSITORY, HELP);
    private static final Map<String, String> ORDERS_EXPLAINED = new HashMap<>();
    static {
        ORDERS_EXPLAINED.put(NICO_MAKE_ENTITY, "make en a new entity or update an existing entity");
        ORDERS_EXPLAINED.put(NICO_MAKE_REPOSITORY, "create a repository for an existing entity");
    }

    private final Scanner scanner;

    public CLIOrchestrator(Scanner pScanner) {
        scanner = pScanner;
        CLIInput.getInstance(scanner);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void run() throws SpringProjectException, NoScannerException, ExitException {
        explain();

        if(ProjectPath.getInstance().hasNotArchitecture()) {
            askArchitecture();
        }

        askMain();
    }

    public void explain() {
        System.out.println(red(bold("Welcome on SpringCLI for CRUD")));
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
        System.out.println("FirstStep : choose your architecture :");
        ArchitectureCLI cli = new ArchitectureCLI(scanner);
        boolean isEntityArchitecture = cli.askArchitecture();
        ProjectPath.getInstance().setEntityArchitecture(isEntityArchitecture);
        System.out.println("You choose " + (isEntityArchitecture ? "Entity" : "Controller") + " architecture");
    }

    private void askMain() throws NoScannerException, ExitException{
        boolean run = true;

        while(run) {
            try {
                System.out.println("CLI is waiting for your orders");
                System.out.println("You could hint /help to know available commands");
                String order = CLIInput.getInstance().askOpenedQuestionWithLimitedChoicesAndPossibleArgument(ORDERS);
                InputOrder input = new InputOrder(order);

                switch (input.getOrder()) {
                    case NICO_MAKE_ENTITY:
                        askCLIEntity(input.getArgument());
                        break;
                    case NICO_MAKE_REPOSITORY:
                        askCLIRepository(input.getArgument());
                        break;
                    case HELP:
                        explainHelp();
                        break;
                    default:
                        run = false;
                }
            } catch (EndOfActionException e){
                run = false;
            }
        }
    }

    private void askCLIEntity(String argument) throws NoScannerException, EndOfActionException, ExitException {
        CLIEntity cli = new CLIEntity();
        cli.ask(argument);
    }

    private void askCLIRepository(String argument) throws NoScannerException, ExitException {
        CLIRepository cli = new CLIRepository();
        try {
            cli.ask(argument);
        } catch (EndOfActionException e) {
            System.out.println("Repository creation aborted");
        }
    }

    private void explainHelp() {
        for (Map.Entry<String, String> entry: ORDERS_EXPLAINED.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
