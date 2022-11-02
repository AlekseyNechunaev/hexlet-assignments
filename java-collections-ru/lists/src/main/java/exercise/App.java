package exercise;

import java.util.LinkedList;
import java.util.List;

public class App {
    public static boolean scrabble(String symbols, String exceptedText) {
        if (symbols.length() < exceptedText.length()) {
            return false;
        }
        List<Character> symbolsList = new LinkedList<>();
        int matches = 0;
        char[] exceptedTextSymbols = exceptedText.toLowerCase().toCharArray();
        for (char symbol : symbols.toCharArray()) {
            symbolsList.add(symbol);
        }
        for (char symbol : exceptedTextSymbols) {
            if (!symbolsList.contains(symbol)) {
                return false;
            }
            matches++;
            symbolsList.remove(Character.valueOf(symbol));
        }
        return exceptedTextSymbols.length == matches;
    }
}
