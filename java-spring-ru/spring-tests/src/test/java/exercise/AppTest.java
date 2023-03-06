package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import exercise.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
// При тестировании можно вообще не запускать сервер
// Spring будет обрабатывать HTTP запрос и направлять его в контроллер
// Код вызывается точно так же, как если бы он обрабатывал настоящий запрос
// Такие тесты обходятся дешевле в плане ресурсов
// Для этого нужно внедрить MockMvc

// BEGIN

// END

// Чтобы исключить влияние тестов друг на друга,
// каждый тест будет выполняться в транзакции.
// После завершения теста транзакция автоматически откатывается
@Transactional
// Для наполнения БД данными перед началом тестирования
// воспользуемся возможностями библиотеки Database Rider
@DBRider
// Файл с данными для тестов (фикстуры)
@DataSet("people.yml")
@AutoConfigureMockMvc
public class AppTest {

    // Автоматическое создание экземпляра класса MockMvc
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRootPage() throws Exception {
        // Выполняем запрос и получаем ответ
        MockHttpServletResponse response = mockMvc
            // Выполняем GET запрос по указанному адресу
            .perform(get("/"))
            // Получаем результат MvcResult
            .andReturn()
            // Получаем ответ MockHttpServletResponse из класса MvcResult
            .getResponse();

        // Проверяем статус ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что ответ содержит определенный текст
        assertThat(response.getContentAsString()).contains("Welcome to Spring");
    }

    @Test
    void testGetPeople() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/people"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что тип содержимого в ответе JSON
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        // Проверяем, что тело ответа содержит данные сущностей
        assertThat(response.getContentAsString()).contains("John", "Smith");
        assertThat(response.getContentAsString()).contains("Jack", "Doe");
    }

    @Test
    void testCreatePerson() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                // Выполняем POST-запрос
                post("/people")
                    // Устанавливаем тип содержимого тела запроса
                    .contentType(MediaType.APPLICATION_JSON)
                    // Добавляем содержимое тела
                    .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        // Проверяем, что сущность добавилась в базу
        // Выполняем GET-запрос на страницу вывода всех сущностей
        MockHttpServletResponse response = mockMvc
            .perform(get("/people"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        // Проверяем, что созданная сущность появилась в базе
        assertThat(response.getContentAsString()).contains("Jackson", "Bind");
    }

    @Test
    void testSuccessGetPersonById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/people/1"))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        Person responseBody = objectMapper.readValue(response.getContentAsString(), Person.class);
        assertThat(responseBody.getId()).isEqualTo(1);
        assertThat(responseBody.getFirstName()).isEqualTo("John");
        assertThat(responseBody.getLastName()).isEqualTo("Smith");
    }

    @Test
    void testSuccessUpdatePerson() throws Exception {
        Person updatePersonData = new Person();
        String updatedFirstName = "Aleksandr";
        String updatedLastName = "Ivanov";
        updatePersonData.setFirstName(updatedFirstName);
        updatePersonData.setLastName(updatedLastName);
        MockHttpServletResponse updateResponse = mockMvc.perform(patch("/people/1")
                .content(objectMapper.writeValueAsString(updatePersonData))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(200);
        MockHttpServletResponse response = mockMvc.perform(get("/people/1"))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        Person responseBody = objectMapper.readValue(response.getContentAsString(), Person.class);
        assertThat(responseBody.getId()).isEqualTo(1);
        assertThat(responseBody.getFirstName()).isEqualTo(updatedFirstName);
        assertThat(responseBody.getLastName()).isEqualTo(updatedLastName);
    }

    @Test
    void testSuccessDeletePerson() throws Exception {
        MockHttpServletResponse deleteResponse = mockMvc.perform(delete("/people/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(200);
        MockHttpServletResponse checkSuccessDeletePersonResponse = mockMvc.perform(get("/people/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(checkSuccessDeletePersonResponse.getStatus()).isEqualTo(200);
        assertThat(checkSuccessDeletePersonResponse.getContentAsString()).isEmpty();
    }
}
