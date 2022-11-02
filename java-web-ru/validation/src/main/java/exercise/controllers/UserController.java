package exercise.controllers;

import exercise.dao.UserDaoImpl;
import exercise.domain.User;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.Validator;
import io.javalin.http.Handler;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class UserController {

    private static UserDaoImpl userDaoImpl = new UserDaoImpl();

    public static Handler listUsers = ctx -> {
        List<User> users = userDaoImpl.getAllUsersOrderedById();
        ctx.attribute("users", users);
        ctx.render("users/index.html");
    };

    public static Handler showUser = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        User user = userDaoImpl.getUserById(id).orElseThrow(NotFoundResponse::new);
        ctx.attribute("user", user);
        ctx.render("users/show.html");
    };

    public static Handler newUser = ctx -> {
        ctx.attribute("errors", Map.of());
        ctx.attribute("user", Map.of());
        ctx.render("users/new.html");
    };

    public static Handler createUser = ctx -> {
        String fistName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        Validator<String> firstNameValidator = ctx.formParamAsClass("firstName", String.class)
                .check(it -> !it.isEmpty(), "Имя не должно быть пустым");
        Validator<String> lastNameValidator = ctx.formParamAsClass("lastName", String.class)
                .check(it -> !it.isEmpty(), "Фамилия не должна быть пустой");
        Validator<String> emailValidator = ctx.formParamAsClass("email", String.class)
                .check(it -> EmailValidator.getInstance().isValid(it), "Некорректный email");
        Validator<String> passwordValidator = ctx.formParamAsClass("password", String.class)
                .check(it -> it.length() >= 4, "Пароль не должен быть короче 4 символов")
                .check(StringUtils::isNumeric, "Пароль должен состоять только из цифр");
        Map<String, List<ValidationError<?>>> errors = JavalinValidation.collectErrors(firstNameValidator,
                lastNameValidator, emailValidator, passwordValidator);
        User user = new User(fistName, lastName, email, password);
        if (!errors.isEmpty()) {
            ctx.status(HttpCode.UNPROCESSABLE_ENTITY);
            ctx.attribute("errors", errors);
            ctx.attribute("user", user);
            ctx.render("users/new.html");
            return;
        }
        userDaoImpl.createUser(user);
        ctx.sessionAttribute("flash", "Пользовать успешно создан");
        ctx.redirect("/users");
    };
}
