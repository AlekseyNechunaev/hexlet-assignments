package exercise;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public final class FileUtils {
    public static String readFileContent(String pathToFile) {
        final Path normalizePath = Path.of(pathToFile).toAbsolutePath().normalize();
        try {
            return Files.readString(normalizePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(String pathToFile, String content) {
        Path normalizePath = normalizePath(pathToFile);
        deleteFile(normalizePath);
        try (OutputStream outputStream = Files.newOutputStream(normalizePath, StandardOpenOption.CREATE_NEW)) {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("error when creating or writing to a file");
        }
    }

    public static Long getDirectorySize(String pathToDirectory) {
        final Path normalizePath = normalizePath(pathToDirectory);
        if (Files.isDirectory(normalizePath)) {
            try (Stream<Path> paths = Files.walk(normalizePath)) {
                return paths.filter(Files::isRegularFile)
                        .mapToLong(file -> {
                            try {
                                return Files.size(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).sum();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("the directory was not found by the passed path");
    }


    private static Path normalizePath(String path) {
        return Path.of(path).toAbsolutePath().normalize();
    }

    private static void deleteFile(Path pathToFile) {
        if (Files.exists(pathToFile)) {
            try {
                Files.delete(pathToFile);
            } catch (IOException e) {
                throw new RuntimeException("the file cannot be deleted");
            }
        }
    }


    private FileUtils() {

    }
}
