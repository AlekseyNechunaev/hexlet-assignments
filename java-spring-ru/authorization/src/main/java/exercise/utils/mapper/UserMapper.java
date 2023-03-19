package exercise.utils.mapper;

import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;
import exercise.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(RegisterUserDto dto);

    GetUserDto map(User user);
}
