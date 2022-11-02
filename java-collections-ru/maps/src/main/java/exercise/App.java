package exercise;

import java.util.*;

public class App {

    public static Map<String, Integer> getWordCount(String sentence) {
        Map<String, Integer> wordCountInSentence = new HashMap<>();
        String trimSentence = sentence.trim();
        if (trimSentence.isEmpty()) {
            return wordCountInSentence;
        }
        String[] wordsFromSentence = trimSentence.split(" ");
        int wordsCounter = 0;
        for (String currentWord : wordsFromSentence) {
            if (!wordCountInSentence.containsKey(currentWord)) {
                for (String otherWord : wordsFromSentence) {
                    if (currentWord.equals(otherWord)) {
                        wordsCounter++;
                    }
                }
                wordCountInSentence.put(currentWord, wordsCounter);
                wordsCounter = 0;
            }
        }
        return wordCountInSentence;
    }

    public static String toString(Map<String, Integer> map) {
        if (map.isEmpty()) {
            return "{}";
        }
        StringBuilder mapElements = new StringBuilder();
        mapElements.append("{").append("\n");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            mapElements.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        mapElements.append("}").append("\n");
        return mapElements.toString();
    }
}