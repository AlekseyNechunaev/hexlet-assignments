package exercise.common.utils;

import exercise.dto.PersonDto;
import exercise.model.Person;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Person mapToEntity(PersonDto dto) {
        Person person = new Person();
        person.setLastName(dto.getLastName());
        person.setFirstName(dto.getFirstName());
        return person;
    }
}
