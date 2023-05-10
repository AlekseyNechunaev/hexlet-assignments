package exercise;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

class App {

    public static void main(String[] args) {

    }

    public static CompletableFuture<String> unionFiles(String pathToFirstFile,
                                                       String pathToSecondFile,
                                                       String savePath) {
        CompletableFuture<String> firstFile = CompletableFuture.supplyAsync(() -> FileUtils.readFileContent(pathToFirstFile));
        CompletableFuture<String> secondFile = CompletableFuture.supplyAsync(() -> FileUtils.readFileContent(pathToSecondFile));
        return firstFile.thenCombine(secondFile, (firstFileContent, secondFileContent) -> {
            final StringBuilder unionContent = new StringBuilder(1024);
            unionContent.append(firstFileContent)
                    .append("\n")
                    .append(secondFileContent);
            String result = unionContent.toString();
            FileUtils.writeToFile(savePath, result);
            return result;
        }).exceptionally((ex) -> {
            System.out.println("NoSuchFileException");
            return null;
        });
    }

    public static CompletableFuture<Long> getDirectorySize(String pathToDirectory) {
        return CompletableFuture.supplyAsync(() -> FileUtils.getDirectorySize(pathToDirectory)).exceptionally((ex) -> {
            throw new RuntimeException(ex.getMessage());
        });
    }
}

