package exercise;

import java.util.List;
import java.util.stream.Collectors;

class App {
    public static List<String> buildAppartmentsList(List<Home> homeList, int countElements) {
        return homeList.stream()
                .sorted((Home::compareTo))
                .limit(countElements)
                .map(Home::toString)
                .collect(Collectors.toList());
    }
}