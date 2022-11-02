package exercise.serivce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Utils {

    private Utils() {

    }

    public static String readFile(Path path) throws IOException {
        return Files.readString(path);
    }
}
