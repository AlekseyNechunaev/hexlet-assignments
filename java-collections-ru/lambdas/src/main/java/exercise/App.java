package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class App {

    public static String[][] enlargeArrayImage(String[][] image) {
        List<String[]> resultImage = new ArrayList<>();
        Arrays.stream(image)
                .map(x -> Arrays.stream(x)
                        .map(y -> new String[]{y, y})
                        .toArray(String[][]::new))
                .map(x -> Arrays.stream(x)
                        .flatMap(Stream::of)
                        .toArray(String[]::new))
                .forEach(x -> {
                    resultImage.add(x);
                    resultImage.add(x);
                });
        return resultImage.toArray(String[][]::new);
    }
}
