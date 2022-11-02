package exercise;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;

public class App {

    public static List<Map<String, String>> findWhere(List<Map<String, String>> books, Map<String, String> where) {
        List<Map<String, String>> coincidences = new LinkedList<>();
        for (Map<String, String> book : books) {
            if (book.entrySet().containsAll(where.entrySet())) {
                coincidences.add(book);
            }
        }
        return coincidences;
    }
}
