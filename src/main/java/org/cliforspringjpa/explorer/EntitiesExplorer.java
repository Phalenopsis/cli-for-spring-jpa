package org.cliforspringjpa.explorer;

import org.cliforspringjpa.project.ProjectPath;
import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.utils.CaseManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitiesExplorer {

    public void findEntities() throws SpringProjectException, IOException {
        String path = ProjectPath.getInstance().getAbsoluteMainPackagePath();
        if (ProjectPath.getInstance().isEntityArchitecture()) {
            findEntitiesInEntityArchitecture(path);
        } else {
            findEntitiesInControllerArchitecture(path);
        }
    }

    private void findEntitiesInControllerArchitecture(String path) throws IOException {
        String modelDir = path + File.separator + "model";
        Set<String> entitiesFiles = listFiles(modelDir);
        for(String fileName: entitiesFiles) {
            String filePath = modelDir + File.separator + fileName;
            EntityParser parser = new EntityParser(filePath);
            parser.parse();
        }
    }

    private void findEntitiesInEntityArchitecture(String path) throws IOException {
        File domainDirFile = new File(path + File.separator + "domain");
        if (domainDirFile.exists() && domainDirFile.isDirectory()) {
            Set<String> directories = listDir(domainDirFile.getAbsolutePath());
            for(String directory: directories) {
                String classDirectoryPath = domainDirFile + File.separator + directory;
                String className = CaseManager.switchToPascalCase(directory);
                String classFileName = className + ".java";
                File entityFile = new File(classDirectoryPath + File.separator + classFileName);
                if (entityFile.exists() && !entityFile.isDirectory()) {
                    EntityParser parser = new EntityParser(entityFile);
                    parser.parse();
                }
            }
        }
    }

    public Set<String> listFiles(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    public Set<String> listDir(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
}
