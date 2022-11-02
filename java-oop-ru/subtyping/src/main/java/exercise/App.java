package exercise;

import java.util.Map;

public class App {
    public static void swapKeyValue(KeyValueStorage storage) {
        Map<String, String> map = storage.toMap();
        map.keySet().forEach(key -> {
            String oldValue = map.get(key);
            storage.unset(key);
            storage.set(oldValue, key);
        });
    }
}
