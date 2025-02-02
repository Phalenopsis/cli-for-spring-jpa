package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.SpringProjectException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestProjectPath {
    @Test
    public void testGetPackageName() throws SpringProjectException {
        ProjectPath projectPath = ProjectPath.getInstance();
        String projectString = projectPath.getPackageName();
        Assertions.assertEquals("org.cliforspringjpa", projectString);
    }
}
