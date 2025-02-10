package org.cliforspringjpa.filecreator;

import org.cliforspringjpa.domain.FileLines;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileFiller {
    FileLines fileLines;
    public FileFiller(FileLines pFileLines) {
        fileLines = pFileLines;
    }

    public void fillFile(File file) {
        try (
                FileOutputStream outputFile = new FileOutputStream(file, false)
        ) {
            writePackage(outputFile);
            writeEmptyLine(outputFile);
            writeImports(outputFile);
            writeEmptyLine(outputFile);
            writeClassDeclaration(outputFile);
            writeClassAttributes(outputFile);
            writeMethods(outputFile);
            writeCloseBlock(outputFile, 0);

        } catch (IOException error) {
            System.err.println(error.getMessage());
        }
    }

    private void writePackage(FileOutputStream pFile) throws IOException {
        String str = "package " + fileLines.getPackageName();
        write(pFile, str);
    }

    private void writeEmptyLine(FileOutputStream pFile) throws IOException {
        write(pFile, "");
    }

    private void writeCloseBlock(FileOutputStream pFile, int index) throws IOException {
        String closed = "}";
        for(int i = 0; i < index; i += 1) {
            closed = "\t" + closed;
        }
        write(pFile, closed);
    }

    private void write(FileOutputStream pFile, String line) throws IOException {
        pFile.write((line + "\n").getBytes(StandardCharsets.UTF_8));
    }

    private void writeImports(FileOutputStream pFile) throws IOException {
        for(String line: fileLines.getImports()) {
            write(pFile, line);
        }
    }

    private void writeClassDeclaration(FileOutputStream pFile) throws IOException {
        for(String line: fileLines.getClassDeclaration()) {
            write(pFile, line);
        }
    }

    private void writeClassAttributes(FileOutputStream pFile) throws IOException {
        for(List<String> lines: fileLines.getAttributes().values()) {
            for(String line: lines) {
                write(pFile, line);
            }
            writeEmptyLine(pFile);
        }
    }

    private void writeMethods(FileOutputStream pFile) throws IOException {
        for(String lines: fileLines.getMethods()) {
            write(pFile, lines);
        }
    }
}
