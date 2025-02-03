package org.cliforspringjpa.utils;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestCaseManager {
    @Test
    public void testSwitchToKebabCase_PascalToKebab() {
        String pascalCase = "PascalCase";
        String result = CaseManager.switchToKebabCase(pascalCase);

        Assertions.assertEquals("pascal-case", result);
    }

    @Test
    public void testSwitchToKebabCase_CamelToKebab() {
        String pascalCase = "camelCase";
        String result = CaseManager.switchToKebabCase(pascalCase);

        Assertions.assertEquals("camel-case", result);
    }

    @Test
    public void testSwitchToKebabCase_WithNumeric() {
        String pascalCase = "camel4Case";
        String result = CaseManager.switchToKebabCase(pascalCase);

        Assertions.assertEquals("camel4-case", result);
    }

    @Test
    public void testSwitchToCamelCase_FormPascalCase() {
        String pascalCase = "PascalCase";
        String result = CaseManager.switchToCamelCase(pascalCase);

        Assertions.assertEquals("pascalCase", result);
    }

    @Test
    public void testSwitchToCamelCase_FromKebabCase() {
        String kebabCase = "kebab-case";
        String result = CaseManager.switchToCamelCase(kebabCase);

        Assertions.assertEquals("kebabCase", result);
    }

    @Test
    public void testSwitchToSnakeCase() {
        String pascalCase = "PascalCaseString";
        String result = CaseManager.switchToSnakeCase(pascalCase);
        Assertions.assertEquals("pascal_case_string", result);
    }

    @Test
    public void testSwitchToPascalCase_FromKebabCase() {
        String kebabCase = "kebab-case";
        String result = CaseManager.switchToPascalCase(kebabCase);

        Assertions.assertEquals("KebabCase", result);
    }

    @Test
    public void testSwitchToPascalCase_FromSnakeCase() {
        String snakeCase = "snake_case";
        String result = CaseManager.switchToPascalCase(snakeCase);

        Assertions.assertEquals("SnakeCase", result);
    }

    @Test
    public void testSwitchToPascalCase_CamelCase() {
        String kebabCase = "camelCase";
        String result = CaseManager.switchToPascalCase(kebabCase);

        Assertions.assertEquals("CamelCase", result);
    }
}
