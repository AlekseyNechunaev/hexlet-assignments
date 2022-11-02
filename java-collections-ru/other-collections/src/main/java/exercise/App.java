package exercise;

import java.util.*;

public class App {
    
    public static Map<String, String> genDiff(Map<String, Object> firstMap, Map<String, Object> secondMap) {
        Map<String, String> result = new LinkedHashMap<>();
        Set<String> allKeys = new HashSet<>(firstMap.keySet());
        allKeys.addAll(secondMap.keySet());
        allKeys.forEach(key -> {
            if (firstMap.containsKey(key) && secondMap.containsKey(key)) {
                if (!firstMap.get(key).equals(secondMap.get(key))) {
                    result.put(key, "changed");
                } else {
                    result.put(key, "unchanged");
                }
            } else {
                if (firstMap.containsKey(key) && !secondMap.containsKey(key)) {
                    result.put(key, "deleted");
                } else {
                    result.put(key, "added");
                }
            }
        });
        return result;
    }
}