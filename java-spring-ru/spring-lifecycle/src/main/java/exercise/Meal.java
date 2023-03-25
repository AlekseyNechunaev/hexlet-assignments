package exercise;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class Meal {

    @PostConstruct
    public void init() {
        System.out.println("init bean meal");
    }
    public String getMealForDaytime(String daytime) {

        switch (daytime) {
            case "morning":
                return "breakfast";
            case "day":
                return "lunch";
            case "evening":
                return "dinner";
            default:
                return "nothing :)";
        }
    }
}
