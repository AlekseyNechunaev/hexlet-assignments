package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @Container
    private static final PostgreSQLContainer<?> DATA_BASE = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("dbname")
            .withUsername("sa")
            .withPassword("sa")
            .withInitScript("init.sql");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATA_BASE::getJdbcUrl);
        registry.add("spring.datasource.username", DATA_BASE::getUsername);
        registry.add("spring.datasource.password", DATA_BASE::getPassword);
    }

    @Test
    void testCreatePerson() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(HttpStatus.OK.value());

        MockHttpServletResponse response = mockMvc
            .perform(get("/people"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Jackson", "Bind");
    }

    @Test
    void testFindAllUsers() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/people")
        ).andReturn()
                .getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Person> responseBody = Arrays.asList(objectMapper.readValue(response.getContentAsString(), Person[].class));
        List<String> lastNames = responseBody.stream()
                .map(Person::getLastName)
                .toList();
        Assertions.assertThat(lastNames).hasSize(4);
        Assertions.assertThat(lastNames).containsAll(List.of("Smith", "Doe", "Simpson", "Lock"));
    }

    @Test
    void testFindById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/people/1")
                ).andReturn()
                .getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Person responseBody = objectMapper.readValue(response.getContentAsString(), Person.class);
        Assertions.assertThat(responseBody.getLastName()).isEqualTo("Smith");
        Assertions.assertThat(responseBody.getFirstName()).isEqualTo("John");
    }

    @Test
    void testUpdateUser() throws Exception {
        final long id = 1;
        MockHttpServletResponse response = mockMvc.perform(
                        patch("/people/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"updateFirstName\", \"lastName\": \"updateLastName\"}")
                ).andReturn()
                .getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Person personFromDb = personRepository.findById(id);
        Assertions.assertThat(personFromDb.getLastName()).isEqualTo("updateLastName");
        Assertions.assertThat(personFromDb.getFirstName()).isEqualTo("updateFirstName");
    }

    @Test
    void testDeleteUser() throws Exception {
        final long id = 1;
        MockHttpServletResponse response = mockMvc.perform(
                        delete("/people/" + id)
                ).andReturn()
                .getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Person personFromDb = personRepository.findById(id);
        Assertions.assertThat(personFromDb).isNull();
    }
}
