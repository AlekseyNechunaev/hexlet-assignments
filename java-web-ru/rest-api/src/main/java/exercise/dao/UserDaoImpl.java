package exercise.dao;

import exercise.domain.User;
import exercise.domain.query.QUser;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao<User, Long> {


    @Override
    public List<User> getAllUsers() {
        return new QUser()
                .findList();
    }

    @Override
    public Optional<User> getUserByID(Long id) {
        return new QUser()
                .id.eq(id)
                .findOneOrEmpty();
    }

    @Override
    public void createUser(User user) {
        user.save();
    }

    @Override
    public void updateUser(User user) {
        user.update();
    }

    @Override
    public void deleteUserById(Long id) {
        new QUser()
                .id.eq(id)
                .delete();
    }
}
