package exercise.mapper;

import exercise.dto.GetUserDto;
import exercise.dto.UserDto;
import exercise.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserDto dto) {
        return new User(dto.getFirstName(), dto.getLastName(), dto.getEmail());
    }

    public GetUserDto map(User user) {
        GetUserDto dto = new GetUserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
