package exercise.controller;

import exercise.dto.ErrorDto;
import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;
import exercise.exception.UserNotFoundException;
import exercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    public List<GetUserDto> getPosts() {
        return userService.findAll();
    }

    @PostMapping(path = "")
    public void createUser(@RequestBody RegisterUserDto user) {
        userService.save(user);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto userNotFound(UserNotFoundException e) {
        return new ErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}

