package exercise;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class FileKVTest {

    @Test
    void fileKVTest() {
        Map<String, String> initValue = Map.of("key", "value", "key2", "value2");
        String pathToFile = "src/test/resources/file";
        KeyValueStorage storage = new FileKV(pathToFile, initValue);
        assertThat(storage.toMap()).isEqualTo(initValue);

        Map<String, String> exceptedStorageAfterAddNewKey = Map.of("key", "value",
                "key2", "value2", "key3", "value3");
        storage.set("key3", "value3");
        assertThat(storage.toMap()).isEqualTo(exceptedStorageAfterAddNewKey);

        Map<String, String> exceptedStorageAfterChangesValue = Map.of("key", "value",
                "key2", "value2", "key3", "newValue3");
        storage.set("key3", "newValue3");
        assertThat(storage.toMap()).isEqualTo(exceptedStorageAfterChangesValue);

        assertThat(storage.get("key", "defalutValue")).isEqualTo("value");

        Map<String, String> exceptedStorageAfterRemove = Map.of(
                "key2", "value2", "key3", "newValue3");
        storage.unset("key");
        assertThat(storage.toMap()).isEqualTo(exceptedStorageAfterRemove);

        assertThat(storage.get("key", "defalutValue")).isEqualTo("defalutValue");

        Map<String, String> map = storage.toMap();
        map.put("key4", "value2");
        assertThat(storage.toMap()).isEqualTo(exceptedStorageAfterRemove);

        Map<String, String> exceptedStorageAfterSwap = Map.of(
                "value2", "key2", "newValue3", "key3");
        App.swapKeyValue(storage);
        assertThat(storage.toMap()).isEqualTo(exceptedStorageAfterSwap);
    }
}
