package exercise.service;

import exercise.dto.GetUserDto;
import exercise.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<GetUserDto> findById(Long id);

    Flux<GetUserDto> findAll();

    Mono<GetUserDto> create(UserDto userDto);

    Mono<GetUserDto> update(Long id, UserDto userDto);

    Mono<Void> delete(Long id);
}
