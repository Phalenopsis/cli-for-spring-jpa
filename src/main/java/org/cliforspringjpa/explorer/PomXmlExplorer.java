package org.cliforspringjpa.explorer;

import org.cliforspringjpa.domain.PackageName;
import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.utils.CaseManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PomXmlExplorer {
    private static PomXmlExplorer instance;
    private final String pomXmlPath;

    public static PomXmlExplorer getInstance() {
        if(Objects.isNull(instance)) {
            return new PomXmlExplorer();
        }
        return instance;
    }

    private PomXmlExplorer() {
        pomXmlPath = getPomXmlPath();
    }

    private String getPomXmlPath() {
        return  System.getProperty("user.dir") + File.separator  + "pom.xml";
    }

    public void verifyPomXml() throws SpringProjectException {
        File pomXmlFile = new File(pomXmlPath);
        if(!(pomXmlFile.exists() && !pomXmlFile.isDirectory())) {
            throw new SpringProjectException("pom.xml file does not exists.");
        }
        //TODO verify Spring and jpa dependencies
    }

    public PackageName getProjectPackage() throws SpringProjectException {
        String packageName = "";
        String alternativePackageName = "";
        String projectName = "Main";
        try (
                BufferedReader reader = new BufferedReader(new FileReader(pomXmlPath))
        ) {
            String line;
            String OPENING_TAG = "<groupId>";
            String CLOSING_TAG = "</groupId>";
            String OPEN_PARENT_TAG = "<parent>";
            String CLOSE_PARENT_TAG = "</parent>";
            String OPEN_DEPENDENCIES_TAG = "<dependencies>";
            String CLOSE_DEPENDENCIES_TAG = "</dependencies>";
            String OPEN_ARTIFACT_TAG = "<artifactId>";
            String CLOSE_ARTIFACT_TAG = "</artifactId>";
            String OPEN_BUILD_TAG = "<build>";
            String CLOSE_BUILD_TAG = "</build>";
            String OPEN_NAME_TAG = "<name>";
            String CLOSE_NAME_TAG = "</name>";
            boolean isInParentSection = false;
            boolean isInDependencies = false;
            boolean isInBuild = false;
            while(Objects.nonNull(line = reader.readLine())) {
                if (line.contains(OPEN_PARENT_TAG)) isInParentSection = true;
                if (line.contains(CLOSE_PARENT_TAG)) isInParentSection = false;
                if (line.contains(OPEN_DEPENDENCIES_TAG)) isInDependencies = true;
                if (line.contains(CLOSE_DEPENDENCIES_TAG)) isInDependencies = false;
                if (line.contains(OPEN_BUILD_TAG)) isInBuild = true;
                if (line.contains(CLOSE_BUILD_TAG)) isInBuild = false;
                if(!isInParentSection && !isInDependencies && !isInBuild
                        && line.contains(OPENING_TAG) && line.contains(CLOSING_TAG) ) {
                    String lineWithoutOpening = line.replace(OPENING_TAG, "");
                    packageName = lineWithoutOpening.replace(CLOSING_TAG, "").trim();
                }
                if(!isInParentSection && !isInDependencies && !isInBuild
                        && line.contains(OPEN_ARTIFACT_TAG) && line.contains(CLOSE_ARTIFACT_TAG)) {
                    String lineWithoutOpening = line.replace(OPEN_ARTIFACT_TAG, "");
                    alternativePackageName = packageName + "." + lineWithoutOpening.replace(CLOSE_ARTIFACT_TAG, "").trim();
                }
                if(!isInParentSection && !isInDependencies && !isInBuild
                        && line.contains(OPEN_NAME_TAG) && line.contains(CLOSE_NAME_TAG)) {
                    String lineWithoutOpening = line.replace(OPEN_NAME_TAG, "");
                    projectName = lineWithoutOpening.replace(CLOSE_NAME_TAG, "").trim() + "Application";
                }
            }
            projectName = parseProjectName(projectName);
            if(isMainInside(packageName, projectName)) {
                return new PackageName(packageName, projectName);
            } else if (isMainInside(alternativePackageName, projectName)) {
                return new PackageName(alternativePackageName, projectName);
            }

        } catch (IOException error) {
            throw new SpringProjectException("Unable to open " + pomXmlPath);
        }
        //return packageName;
        throw new SpringProjectException("Error in PomXmlExplorer");
    }

    private String parseProjectName(String projectName) {
        String[] arr = projectName.split(" ");
        return Arrays.stream(arr).map(CaseManager::switchToPascalCase).collect(Collectors.joining());
    }

    private boolean isMainInside(String packageName, String projectName) {
        String packagePath = getDirectoryPath(packageName);
        String javaPath = System.getProperty("user.dir") + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "java" + File.separator
                + packagePath + File.separator
                + projectName + ".java";

        File main = new File(javaPath);
        return main.exists();
    }

    public String getDirectoryPath(String packageName) {
        String directoryPath = "";
        for(char c: packageName.toCharArray()) {
            if(c == '.') {
                directoryPath = directoryPath + File.separator;
            } else {
                directoryPath = directoryPath + c;
            }
        }
        return directoryPath;
    }
}
