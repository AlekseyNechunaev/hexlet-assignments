package exercise.service;

import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;
import exercise.exception.UserNotFoundException;
import exercise.model.User;
import exercise.rabbit.MessageSender;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSender sender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MessageSender sender) {
        this.userRepository = userRepository;
        this.sender = sender;
    }

    @Override
    public GetUserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        return new GetUserDto(user.getId(), user.getName());

    }

    @Override
    public List<GetUserDto> findAll() {
       List<User> users = userRepository.findAll();
       return users.stream()
               .map(user -> new GetUserDto(user.getId(), user.getName()))
               .collect(Collectors.toList());
    }

    @Override
    public void save(RegisterUserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        userRepository.save(user);
        sender.sendMessage("User " + user.getName() + " has been registered");
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        userRepository.delete(user);
        sender.sendMessage("User " + user.getName() + " has been deleted");
    }
}
