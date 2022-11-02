package exercise;

import io.ebean.Transaction;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;
import io.ebean.DB;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.Database;

class AppTest {

    private static Javalin app;
    private static String baseUrl;
    private static Transaction transaction;

    @BeforeAll
    static void initApp() {
        app = App.getApp();
        app.start(0);
        int port = app.port();
        baseUrl = "http://localhost:" + port;

    }

    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("user");
        User existingUser = new User("Wendell", "Legros", "a@a.com", "123456");
        existingUser.save();
        transaction = db.beginTransaction();
    }

    @Test
    void testUsers() {

        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/users")
                .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("Wendell Legros");
    }

    @Test
    void testNewUser() {

        HttpResponse<String> response = Unirest
                .get(baseUrl + "/users/new")
                .asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testCreateUser() {
        User user = new User("Ivan", "Ivanov", "ivanov@gmail.com", "ivanov123");
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", user.getFirstName())
                .field("lastName", user.getLastName())
                .field("email", user.getEmail())
                .field("password", user.getPassword())
                .asString();
        assertThat(response.getStatus()).isEqualTo(302);
        User userOnBase = new QUser()
                .email.eq(user.getEmail())
                .findOne();
        assertThat(userOnBase).isNotNull();
        assertThat(userOnBase.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userOnBase.getLastName()).isEqualTo(user.getLastName());
        assertThat(userOnBase.getEmail()).isEqualTo(user.getEmail());
        assertThat(userOnBase.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testCreateUserWithInvalidFirstName() {
        User user = new User("", "Ivanov", "ivanov@gmail.com", "ivanov123");
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", user.getFirstName())
                .field("lastName", user.getLastName())
                .field("email", user.getEmail())
                .field("password", user.getPassword())
                .asString();
        assertThat(response.getStatus()).isEqualTo(422);
        User userOnBase = new QUser()
                .email.eq(user.getEmail())
                .findOne();
        assertThat(userOnBase).isNull();
        assertThat(response.getBody()).contains("Имя не должно быть пустым");
    }

    @Test
    void testCreateUserWithInvalidLastName() {
        User user = new User("Ivan", "", "ivanov@gmail.com", "ivanov123");
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", user.getFirstName())
                .field("lastName", user.getLastName())
                .field("email", user.getEmail())
                .field("password", user.getPassword())
                .asString();
        assertThat(response.getStatus()).isEqualTo(422);
        User userOnBase = new QUser()
                .email.eq(user.getEmail())
                .findOne();
        assertThat(userOnBase).isNull();
        assertThat(response.getBody()).contains("Фамилия не должна быть пустой");
    }

    @Test
    void testCreateUserWithInvalidEmail() {
        User user = new User("Ivan", "Ivanov", "ivanov@gm", "ivanov123");
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", user.getFirstName())
                .field("lastName", user.getLastName())
                .field("email", user.getEmail())
                .field("password", user.getPassword())
                .asString();
        assertThat(response.getStatus()).isEqualTo(422);
        User userOnBase = new QUser()
                .email.eq(user.getEmail())
                .findOne();
        assertThat(userOnBase).isNull();
        assertThat(response.getBody()).contains("Должно быть валидным email");
    }

    @Test
    void testCreateUserWithInvalidPassword() {
        User user = new User("Ivan", "Ivanov", "ivanov@gmail.com", "iva");
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", user.getFirstName())
                .field("lastName", user.getLastName())
                .field("email", user.getEmail())
                .field("password", user.getPassword())
                .asString();
        assertThat(response.getStatus()).isEqualTo(422);
        User userOnBase = new QUser()
                .email.eq(user.getEmail())
                .findOne();
        assertThat(userOnBase).isNull();
        assertThat(response.getBody()).contains("Пароль должен содержать не менее 4 символов");
    }

    @AfterEach
    void afterEach() {
        transaction.rollback();
    }

    @AfterAll
    static void stopApp() {
        app.stop();
    }
}
