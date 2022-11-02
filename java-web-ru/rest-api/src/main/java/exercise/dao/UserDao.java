package exercise.dao;

import java.util.List;
import java.util.Optional;

public interface UserDao<T, R> {

    List<T> getAllUsers();

    Optional<T> getUserByID(R id);

    void createUser(T user);

    void updateUser(T user);

    void deleteUserById(R id);


}
