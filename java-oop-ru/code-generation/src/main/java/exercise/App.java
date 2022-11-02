package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {

    public static void save(Path filePath, Car car) {
        try {
            Files.writeString(filePath, car.serialize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Car extract(Path filePath) {
        String fileContent;
        try {
            fileContent = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Car.unserialize(fileContent);
    }
}
