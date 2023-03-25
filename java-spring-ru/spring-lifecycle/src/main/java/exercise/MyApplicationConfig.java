package exercise;

import exercise.daytimes.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MyApplicationConfig {

    private final int MORNING_LOWER_BOUND = 6;
    private final int MORNING_UPPER_BOUND = 11;
    private final int DAY_LOWER_BOUND = 12;
    private final int DAY_UPPER_BOUND = 17;
    private final int EVENING_LOWER_BOUND = 18;
    private final int EVENING_UPPER_BOUND = 22;

    @Bean
    public Daytime daytime() {
        int hourNow = LocalDateTime.now().getHour();
        if (hourNow >= MORNING_LOWER_BOUND && hourNow <= MORNING_UPPER_BOUND) {
            return new Morning();
        } else if (hourNow >= DAY_LOWER_BOUND && hourNow <= DAY_UPPER_BOUND) {
            return new Day();
        } else if (hourNow >= EVENING_LOWER_BOUND && hourNow <= EVENING_UPPER_BOUND) {
            return new Evening();
        }
        return new Night();
    }
}
