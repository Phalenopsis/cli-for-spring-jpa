package org.cliforspringjpa.domain;

public class PackageName {
    private final String packageName;
    private final String projectName;

    public PackageName(String pPackageName, String pProjectName) {
        packageName = pPackageName;
        projectName = pProjectName;

    }
    public String getProjectName() {
        return projectName;
    }

    public String getPackageName() {
        return packageName;
    }
}
