package exercise.dto;

public class ShortCityDto {
    private int temperature;
    private String name;

    public ShortCityDto(int temperature, String name) {
        this.temperature = temperature;
        this.name = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
