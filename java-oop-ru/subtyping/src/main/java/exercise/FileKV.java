package exercise;

import java.util.Map;

public class FileKV implements KeyValueStorage {

    private final String pathToFile;

    public FileKV(String pathToFile, Map<String, String> storage) {
        this.pathToFile = pathToFile;
        this.insertToStorage(storage);
    }
    @Override
    public void set(String key, String value) {
        Map<String, String> storage = selectFromStorage();
        storage.put(key, value);
        insertToStorage(storage);
    }

    @Override
    public void unset(String key) {
        Map<String, String> storage = selectFromStorage();
        storage.remove(key);
        insertToStorage(storage);
    }

    @Override
    public String get(String key, String defaultValue) {
        Map<String, String> storage = selectFromStorage();
        return storage.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return selectFromStorage();
    }

    private Map<String, String> selectFromStorage() {
        String content = Utils.readFile(pathToFile);
        return Utils.unserialize(content);
    }

    private void insertToStorage(Map<String, String> storage) {
        String json = Utils.serialize(storage);
        Utils.writeFile(pathToFile, json);
    }

}