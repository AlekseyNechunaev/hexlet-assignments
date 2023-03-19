package exercise.utils.mapper;

import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;
import exercise.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User map(RegisterUserDto dto);

    GetUserDto map(User user);
}
