package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.NoScannerException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestInputOrder {

    @Test
    public void testConstructor_NoArgument_ReturnEmptyString() throws NoScannerException {
        String order = "nico make entity";
        InputOrder inputOrder = new InputOrder(order);
        String argument = inputOrder.getArgument();
        Assertions.assertEquals("", argument);
    }

    @Test
    public void testConstructor_Argument_ReturnArgument() {
        String order = "nico make entity Book";
        InputOrder inputOrder = new InputOrder(order);
        String argument = inputOrder.getArgument();
        Assertions.assertEquals("Book", argument);
    }

    @Test
    public void testConstructor_LowerCaseArgument_ReturnPascalCaseArgument() {
        String order = "nico make entity book";
        InputOrder inputOrder = new InputOrder(order);
        String argument = inputOrder.getArgument();
        Assertions.assertEquals("Book", argument);
    }
}
