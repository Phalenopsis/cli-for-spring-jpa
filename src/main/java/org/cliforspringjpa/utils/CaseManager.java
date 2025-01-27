package org.cliforspringjpa.utils;

public class CaseManager {
    public static boolean isPascalCase(String string) {
        return string.matches("[A-Z][a-z0-9A-Z]*");
    }

    public static boolean isCamelCase(String string) {
        return string.matches("[a-z][a-z0-9A-Z]*");
    }

    public static String switchToKebabCase(String string) {
        String str = "";
        String camelString = string.substring(0, 1).toLowerCase() + string.substring(1);
        for(char c: camelString.toCharArray()) {
            if(Character.isLetter(c) && Character.isUpperCase(c)) {
                str = str + "-" + Character.toLowerCase(c);
            } else {
                str = str + c;
            }
        }
        return str;
    }

    public static String switchToCamelCase(String string) {
        String str = "";
        String camelString = string.substring(0, 1).toLowerCase() + string.substring(1);
        boolean mustUp = false;
        for(char c: camelString.toCharArray()) {
            if(c == '-') {
                mustUp = true;
            } else if (mustUp) {
                str = str + Character.toUpperCase(c);
                mustUp = false;
            } else {
                str = str + c;
            }
        }
        return str;
    }

    public static String switchToSnakeCase(String name) {
        String kebabCase = switchToKebabCase(name);
        return kebabCase.replaceAll("-", "_");
    }
}
