package org.cliforspringjpa.filecreator;

import org.cliforspringjpa.domain.FileLines;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCreator {
    private FileLines fileLines;

    public FileCreator(FileLines pFileLines) {
        this.fileLines = pFileLines;
    }

    public void createDirectory() throws IOException {
        String absoluteDirectoryPath = fileLines.getDirectoryPath();
        try {
            Files.createDirectory(Paths.get(absoluteDirectoryPath
            ));
        } catch (FileAlreadyExistsException ignored) {
            System.out.println("ignored");
        }
    }

    public void createFile() {
        File file = new File(fileLines.getDirectoryPath() + File.separator + fileLines.getClassName() + ".java");
        try {
            if(file.createNewFile()) {
                FileFiller filler = new FileFiller(fileLines);
                filler.fillFile(file);
            } else {
                System.out.println("Could not generate " + fileLines.getDirectoryPath() + File.separator + fileLines.getClassName() + ".java");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println(fileLines.getDirectoryPath() + File.separator + fileLines.getClassName() + " : unable to fill file");
        }
    }
}
