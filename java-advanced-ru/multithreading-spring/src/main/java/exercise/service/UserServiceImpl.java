package exercise.service;

import exercise.dto.GetUserDto;
import exercise.dto.UserDto;
import exercise.mapper.UserMapper;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<GetUserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(mapper::map);
    }

    @Override
    public Flux<GetUserDto> findAll() {
        return userRepository.findAll()
                .map(mapper::map);
    }

    @Override
    public Mono<GetUserDto> create(UserDto userDto) {
        User newUser = mapper.map(userDto);
        return userRepository.save(newUser)
                .map(mapper::map);
    }

    @Override
    public Mono<GetUserDto> update(Long id, UserDto userDto) {
        Mono<User> user = userRepository.findById(id);
        return user.flatMap(updatableUser -> {
                    updatableUser.setEmail(userDto.getEmail());
                    updatableUser.setFirstName(userDto.getFirstName());
                    updatableUser.setLastName(userDto.getLastName());
                    return Mono.just(updatableUser);
                }).flatMap(userRepository::save)
                .map(mapper::map);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return userRepository.deleteById(id);
    }
}
