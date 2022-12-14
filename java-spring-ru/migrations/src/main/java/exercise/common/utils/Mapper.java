package exercise.common.utils;

import exercise.dto.AddPeopleRequestDto;
import exercise.entity.People;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public People mapToEntity(AddPeopleRequestDto dto) {
        People people = new People();
        people.setFirstName(dto.getFirstName());
        people.setLastName(dto.getLastName());
        return people;
    }
}
