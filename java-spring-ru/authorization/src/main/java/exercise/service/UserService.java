package exercise.service;

import exercise.model.User;

import java.util.List;

public interface UserService {

    void create(User user);

    List<User> findAll();

    User findById(Long id);

    void delete(Long id);
}
