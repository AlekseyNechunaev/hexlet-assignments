package exercise.service;

import exercise.exception.UserExistsByEmailException;
import exercise.exception.UserExistsByUserNameException;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(User user) {
        Optional<User> userByLogin = userRepository.findByUsername(user.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByLogin.isPresent()) {
            throw new UserExistsByUserNameException("a user with this username already exists, try logging in");
        }
        if (userByEmail.isPresent()) {
            throw new UserExistsByEmailException("a user with this email already exists, try logging in");
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
