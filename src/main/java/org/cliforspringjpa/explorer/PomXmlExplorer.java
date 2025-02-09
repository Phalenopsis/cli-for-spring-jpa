package org.cliforspringjpa.explorer;

import org.cliforspringjpa.exception.SpringProjectException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

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

    public String getProjectPackage() throws SpringProjectException {
        String packageName = "";
        try (
                BufferedReader reader = new BufferedReader(new FileReader(pomXmlPath))
        ) {
            String line;
            while(Objects.nonNull(line = reader.readLine())) {
                boolean isInParentSection = false;
                String OPEN_PARENT_TAG = "<parent>";
                String CLOSE_PARENT_TAG = "</parent>";
                if (line.contains(OPEN_PARENT_TAG)) isInParentSection = true;
                if (line.contains(CLOSE_PARENT_TAG)) isInParentSection = false
                String OPENING_TAG = "<groupId>";
                String CLOSING_TAG = "</groupId>";
                if(!isInParentSection && line.contains(OPENING_TAG) && line.contains(CLOSING_TAG) ) {
                    String lineWithoutOpening = line.replace(OPENING_TAG, "");
                    packageName = lineWithoutOpening.replace(CLOSING_TAG, "").trim();
                    break;
                }
            }
        } catch (IOException error) {
            throw new SpringProjectException("Unable to open " + pomXmlPath);
        }
        return packageName;
    }
}
