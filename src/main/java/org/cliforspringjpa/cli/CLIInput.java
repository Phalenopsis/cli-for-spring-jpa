package org.cliforspringjpa.cli;

import org.cliforspringjpa.exception.EndOfActionException;
import org.cliforspringjpa.exception.ExitException;
import org.cliforspringjpa.exception.NoScannerException;
import org.cliforspringjpa.utils.CaseManager;

import java.util.*;

public class CLIInput {
    private static CLIInput instance;
    private final Scanner scanner;

    private final Set<String> specialResponses = Set.of("\\help");

    public static CLIInput getInstance() throws NoScannerException {
        if(Objects.isNull(instance)) {
            throw new NoScannerException("No scanner for CLIInput");
        }
        return instance;
    }

    public static CLIInput getInstance(Scanner scanner) {
        if(Objects.isNull(instance)) {
            instance = new CLIInput(scanner);
        }
        return instance;
    }

    private CLIInput(Scanner pScanner) {
        scanner = pScanner;
    }

    public Boolean askBooleanQuestion() {
        String response = getInput().toLowerCase(Locale.ROOT).trim();
        while (!response.isEmpty() && !response.equals("n") && !response.equals("y")) {
            System.out.println("I don't understand. Please hint Y or N");
            response = getInput().toLowerCase(Locale.ROOT);
        }
        return !response.equals("n");
    }

    public String askOpenedPascalCaseQuestion() {
        String response;
        response = getInput();
        while(!CaseManager.isPascalCase(response)) {
            System.out.println("Please use PascalCase");
            response = getInput();

        }
        return response;
    }

    public String askOpenedQuestionWithLimitedChoices(Set<String> allowedResponses) throws ExitException, EndOfActionException {
        String response;
        response = getInput();
        while(!allowedResponses.contains(response)) {
            checkExit(response);
            System.out.println("I don't understand.");
            response = getInput();

        }
        return response;
    }

    public String askOpenedQuestionWithLimitedChoicesAndPossibleArgument(Set<String> allowedResponses) throws ExitException, EndOfActionException {
        String response;
        response = getInput();
        while(!isContaining(allowedResponses, response)) {
            checkExit(response);
            System.out.println("I don't understand.");
            response = getInput();

        }
        System.out.println(response);
        return response;
    }

    private boolean isContaining(Set<String> allowedResponses, String response) {
        for(String allowedResponse: allowedResponses) {
            if(response.startsWith(allowedResponse)) {
                return true;
            }
        }
        return false;
    }

    private void checkExit(String response) throws ExitException, EndOfActionException {
        if (response.equalsIgnoreCase("exit")) {
            throw new ExitException();
        }
        if(response.isEmpty()) {
            throw new EndOfActionException();
        }
    }

    public String askOpenedQuestion() throws ExitException, EndOfActionException {
        String response;
        response = getInput();
        checkExit(response);
        return response;
    }

    String getInput() {
        System.out.print(">> ");
        return scanner.nextLine();
    }
}
