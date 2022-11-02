package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;

import java.io.IOException;

@Value
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;
    private static final ObjectMapper mapper = new ObjectMapper();

    public String serialize() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Car unserialize(String jsonContent) {
        try {
            return mapper.readValue(jsonContent, Car.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
