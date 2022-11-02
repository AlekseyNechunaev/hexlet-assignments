package exercise;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SorterTest {

    @Test
    void takeOldestManTest() {
        List<Map<String, String>> users = List.of(
                Map.of("name", "Vladimir Nikolaev", "birthday", "1990-12-27", "gender", "male"),
                Map.of("name", "Andrey Petrov", "birthday", "1989-11-23", "gender", "male"),
                Map.of("name", "Anna Sidorova", "birthday", "1996-09-09", "gender", "female"),
                Map.of("name", "John Smith", "birthday", "1989-03-11", "gender", "male"),
                Map.of("name", "Vanessa Vulf", "birthday", "1985-11-16", "gender", "female"),
                Map.of("name", "Alice Lucas", "birthday", "1986-01-01", "gender", "female"),
                Map.of("name", "Elsa Oscar", "birthday", "1970-03-10", "gender", "female")
        );
        List<String> exceptedList = List.of("John Smith", "Andrey Petrov", "Vladimir Nikolaev");
        assertThat(Sorter.takeOldestMans(users)).isEqualTo(exceptedList);
    }

    @Test
    void takeOldestManWithEmptyListTest() {
        List<Map<String, String>> users = new ArrayList<>();
        assertThat(Sorter.takeOldestMans(users)).isEmpty();
    }

    @Test
    void takeOldestManWithListOfWhereAllGenderMaleTest() {
        List<Map<String, String>> users = List.of(
                Map.of("name", "Anna Sidorova", "birthday", "1996-09-09", "gender", "female"),
                Map.of("name", "Vanessa Vulf", "birthday", "1985-11-16", "gender", "female"),
                Map.of("name", "Alice Lucas", "birthday", "1986-01-01", "gender", "female"),
                Map.of("name", "Elsa Oscar", "birthday", "1970-03-10", "gender", "female")
        );
        assertThat(Sorter.takeOldestMans(users)).isEmpty();
    }

    @Test
    void takeOldestManWhereMaleHaveTheSameBirthDayTest() {
        List<Map<String, String>> users = List.of(
                Map.of("name", "Vladimir Nikolaev", "birthday", "1990-12-27", "gender", "male"),
                Map.of("name", "Andrey Petrov", "birthday", "1990-12-27", "gender", "male"),
                Map.of("name", "Anna Sidorova", "birthday", "1996-09-09", "gender", "female"),
                Map.of("name", "John Smith", "birthday", "1990-12-27", "gender", "male"),
                Map.of("name", "Vanessa Vulf", "birthday", "1985-11-16", "gender", "female"),
                Map.of("name", "Alice Lucas", "birthday", "1986-01-01", "gender", "female"),
                Map.of("name", "Elsa Oscar", "birthday", "1970-03-10", "gender", "female")
        );
        List<String> exceptedList = List.of("Vladimir Nikolaev", "Andrey Petrov", "John Smith");
        assertThat(Sorter.takeOldestMans(users)).isEqualTo(exceptedList);
    }
}


