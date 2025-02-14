package org.cliforspringjpa.explorer;

import org.cliforspringjpa.domain.ParsedEntity;
import org.cliforspringjpa.project.Project;

import java.io.*;
import java.util.Objects;

public class EntityParser {
    File file;

    public EntityParser(String pFilePath) {
        file = new File(pFilePath);
    }

    public EntityParser(File pFile) {
        file = pFile;
    }

    public void parse() {
        try(BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            String entityName = file.getName().substring(0, file.getName().length() - ".java".length());
            ParsedEntity parsedEntity = new ParsedEntity(entityName);

            while(Objects.nonNull(line = reader.readLine())) {
                parsedEntity.parseLine(line);
            }
            Project.getInstance().addParsedEntity(parsedEntity);

        } catch (FileNotFoundException e) {
            System.err.println("File " + file.getName() + " not found");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Another error than file not found int EntityParser.parse()");
            System.out.println("If this message appear, please do a issue on GitHub and explain context.");
            System.err.println(e.getMessage());
        }
    }
}
