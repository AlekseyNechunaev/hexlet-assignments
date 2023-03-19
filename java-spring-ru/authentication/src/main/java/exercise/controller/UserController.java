package exercise.controller;

import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;
import exercise.model.User;
import exercise.service.UserService;
import exercise.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

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

    @PostMapping
    public void register(@RequestBody RegisterUserDto dto) {
        userService.create(userMapper.map(dto));
    }
}
