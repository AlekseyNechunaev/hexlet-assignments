package exercise.controller;

import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;
import exercise.model.User;
import exercise.service.UserService;
import exercise.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private static final String OK_RESPONSE = "OK";

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "")
    public List<GetUserDto> getUsers() {
        return userService.findAll().stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public GetUserDto getUser(@PathVariable Long id) {
        return userMapper.map(userService.findById(id));
    }

    @PostMapping(path = "")
    public String register(@RequestBody RegisterUserDto user) {
        userService.create(userMapper.map(user));
        return OK_RESPONSE;
    }

    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.delete(id);
        return OK_RESPONSE;
    }
}
