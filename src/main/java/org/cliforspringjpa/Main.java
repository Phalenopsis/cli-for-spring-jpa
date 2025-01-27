package org.cliforspringjpa;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.explorer.SpringProjectValidator;

public class Main {
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
            //TODO
        }
    }
}