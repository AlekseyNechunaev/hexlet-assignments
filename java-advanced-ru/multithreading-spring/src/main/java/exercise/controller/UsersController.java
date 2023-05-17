package exercise.controller;

import exercise.dto.GetUserDto;
import exercise.dto.UserDto;
import exercise.model.User;
import exercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<GetUserDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<GetUserDto> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public Mono<GetUserDto> create(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @PatchMapping(path = "/{id}")
    public Mono<GetUserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
