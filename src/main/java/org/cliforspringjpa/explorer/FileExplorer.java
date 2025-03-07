package org.cliforspringjpa.explorer;

import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.project.ProjectPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileExplorer {
    public void find() throws SpringProjectException, IOException {
        String path = ProjectPath.getInstance().getAbsoluteMainPackagePath();
        if (ProjectPath.getInstance().isEntityArchitecture()) {
            findInEntityArchitecture(path);
        } else {
            findInLayerArchitecture(path);
        }
    }

    private Set<String> listFiles(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    private Set<String> listDir(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    private void findInLayerArchitecture(String path) throws IOException {
        for (ClassType directory : ClassType.values()) {
            String modelDir = path + File.separator + directory;
            Set<String> entitiesFiles = listFiles(modelDir);
            for (String fileName : entitiesFiles) {
                String filePath = modelDir + File.separator + fileName;
                FileParser parser = new FileParser(filePath, directory);
                parser.parse();
            }
        }
    }

    private void findInEntityArchitecture(String path) throws IOException {
        File domainDirFile = new File(path + File.separator + "domain");
        if (domainDirFile.exists() && domainDirFile.isDirectory()) {
            Set<String> directories = listDir(domainDirFile.getAbsolutePath());
            for (String directory : directories) {
                String classDirectoryPath = domainDirFile + File.separator + directory;
                Set<String> files = listFiles(classDirectoryPath);
                for(String file: files) {
                    String classPath = classDirectoryPath + File.separator + file;
                    ClassType classType = getClassType(file);
                    FileParser parser = new FileParser(classPath, classType);
                    parser.parse();
                }
            }
        }
    }

    private ClassType getClassType(String className) {
        ClassType classType = null;
        for(ClassType possibleClassType: ClassType.values()) {
            if(className.toLowerCase().contains(possibleClassType.getValue())) {
                classType = possibleClassType;
            }
        }
        if(Objects.isNull(classType)) {
            classType = ClassType.MODEl;
        }
        return classType;
    }
}
