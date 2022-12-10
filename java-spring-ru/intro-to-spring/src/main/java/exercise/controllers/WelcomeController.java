package exercise.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WelcomeController.PATH)
public class WelcomeController {

    public static final String PATH = "/";

    @GetMapping
    public String welcomeSpring() {
        return "Welcome to Spring";
    }

    @GetMapping("/hello")
    public String welcomeName(@RequestParam(required = false) String name) {
        return name == null || name.isBlank() ? "Hello, World" : "Hello, " + name;
    }
}