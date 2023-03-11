package exercise.service;

import com.querydsl.core.types.Predicate;
import exercise.model.User;

public interface UserService {

    Iterable<User> findAllByCriteria(Predicate predicate);
}
