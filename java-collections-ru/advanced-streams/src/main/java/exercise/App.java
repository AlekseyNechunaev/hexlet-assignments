package exercise;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.stream.Stream;

public class App {

    public static String getForwardedVariables(String data) {
        return Arrays.stream(data.split("\n"))
                .filter(line -> line.startsWith("environment="))
                .map(line -> line.replaceAll("environment=", "").replaceAll("\"", ""))
                .map(word -> word.split(","))
                .flatMap(Stream::of)
                .filter(word -> word.startsWith("X_FORWARDED_"))
                .map(word -> word.replaceAll("X_FORWARDED_", ""))
                .collect(Collectors.joining(","));
    }
}