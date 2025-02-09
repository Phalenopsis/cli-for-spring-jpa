package org.cliforspringjpa.domain;

import org.cliforspringjpa.cli.CLIOrchestrator;
import org.cliforspringjpa.utils.CaseManager;

import java.util.Objects;

public class InputOrder {
    private String order;
    private String argument;

    public InputOrder(String responseOrder) {
        for(String existingOrder : CLIOrchestrator.ORDERS) {
            if(responseOrder.startsWith(existingOrder) && responseOrder.length() > existingOrder.length()) {
                argument = responseOrder.substring(existingOrder.length()).trim();
                argument = CaseManager.isPascalCase(argument) ? argument : CaseManager.switchToPascalCase(argument);
                order = existingOrder;
                break;
            }
        }
        if(Objects.isNull(argument)) {
            order = responseOrder;
            argument = "";
        }
    }

    public String getOrder() {
        return order;
    }

    public String getArgument() {
        return argument;
    }
}
