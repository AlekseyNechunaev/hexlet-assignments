package exercise.service;

import exercise.exception.ResourceNotFoundException;
import exercise.exception.UserExistsByEmailException;
import exercise.model.User;
import exercise.model.UserRole;
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
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserExistsByEmailException("the user with this email is already registered, try logging in");
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user with this id not found"));
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
