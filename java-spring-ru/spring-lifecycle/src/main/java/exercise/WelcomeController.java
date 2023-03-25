package exercise;

import exercise.daytimes.Day;
import exercise.daytimes.Daytime;
import exercise.daytimes.Evening;
import exercise.daytimes.Morning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/daytime")
public class WelcomeController {

    private final Daytime daytime;

    @Autowired
    public WelcomeController(Daytime daytime) {
        this.daytime = daytime;
    }

    @GetMapping
    public String dayTime() {
        return generateAnswer();
    }

    private String generateAnswer() {
        String pattern = "it is " + daytime.getName() + " now. Enjoy you %s";
        if (daytime instanceof Morning) {
            return String.format(pattern, "breakfast");
        }
        if (daytime instanceof Day) {
            return String.format(pattern, "lunch");
        }
        if(daytime instanceof Evening) {
            return String.format(pattern, "dinner");
        } else {
            return String.format(pattern, "dinner or breakfast :)");
        }
    }
}