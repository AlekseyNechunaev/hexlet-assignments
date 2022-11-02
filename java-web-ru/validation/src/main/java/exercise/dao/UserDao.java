package exercise.dao;

import java.util.List;
import java.util.Optional;

public interface UserDao<T, K> {

    List<T> getAllUsersOrderedById();

    Optional<T> getUserById(K id);

    void createUser(T user);
}
