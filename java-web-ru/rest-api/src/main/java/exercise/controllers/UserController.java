package exercise.controllers;

import exercise.dao.UserDaoImpl;
import exercise.domain.User;
import io.ebean.DB;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserController implements CrudHandler {

    private final UserDaoImpl userDao;

    public UserController() {
        userDao = new UserDaoImpl();
    }

    @Override
    public void getAll(@NotNull Context ctx) {
        List<User> users = userDao.getAllUsers();
        ctx.json(DB.json().toJson(users));
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull String id) {
        long numberID = Long.parseLong(id);
        User user = userDao.getUserByID(numberID).orElseThrow(NotFoundResponse::new);
        ctx.json(DB.json().toJson(user));
    }

    @Override
    public void create(@NotNull Context ctx) {
        User user = DB.json().toBean(User.class, ctx.body());
        userDao.createUser(user);
    }


    @Override
    public void update(@NotNull Context ctx, @NotNull String id) {
        long numberID = Long.parseLong(id);
        userDao.getUserByID(numberID).orElseThrow(NotFoundResponse::new);
        User userFromRequest = DB.json().toBean(User.class, ctx.body());
        userFromRequest.setId(numberID);
        userDao.updateUser(userFromRequest);
    }


    @Override
    public void delete(@NotNull Context ctx, @NotNull String id) {
        long numberID = Long.parseLong(id);
        userDao.deleteUserById(numberID);
    }
}
