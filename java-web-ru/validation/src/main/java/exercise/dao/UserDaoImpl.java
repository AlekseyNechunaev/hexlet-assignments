package exercise.dao;

import exercise.domain.User;
import exercise.domain.query.QUser;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao<User, Long> {

    @Override
    public List<User> getAllUsersOrderedById() {
        return new QUser()
                .orderBy()
                .id.asc()
                .findList();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return new QUser()
                .id.eq(id)
                .findOneOrEmpty();
    }

    @Override
    public void createUser(User user) {
        user.save();
    }
}
