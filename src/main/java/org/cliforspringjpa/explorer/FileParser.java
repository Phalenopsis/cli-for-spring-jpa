package org.cliforspringjpa.explorer;

import org.cliforspringjpa.exception.ParsedFileFactoryException;
import org.cliforspringjpa.explorer.parsedFile.ParsedFile;
import org.cliforspringjpa.explorer.parsedFile.ParsedFileFactory;
import org.cliforspringjpa.project.Project;

import java.io.*;
import java.util.Objects;

public class FileParser {
    private final File file;
    private final ClassType classType;

    public FileParser(String pFilePath, ClassType pClassType) {
        file = new File(pFilePath);
        classType = pClassType;
    }

    public void parse() {
        try(BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            String className = file.getName().substring(0, file.getName().length() - ".java".length());
            ParsedFile parsedFile = ParsedFileFactory.build(className, classType);
            while(Objects.nonNull(line = reader.readLine())) {
                parsedFile.parseLine(line);
            }
            Project.getInstance().addParsedFile(parsedFile);

        } catch (FileNotFoundException e) {
            System.err.println("File " + file.getName() + " not found");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Another error than file not found int EntityParser.parse()");
            System.out.println("If this message appear, please do a issue on GitHub and explain context.");
            System.err.println(e.getMessage());
        } catch (ParsedFileFactoryException e) {
            System.err.println(e.getMessage());
        }
    }
}
