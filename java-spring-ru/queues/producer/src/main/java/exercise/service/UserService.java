package exercise.service;

import exercise.dto.GetUserDto;
import exercise.dto.RegisterUserDto;

import java.util.List;

public interface UserService {

    GetUserDto findById(Long id);

    List<GetUserDto> findAll();

    void save(RegisterUserDto dto);

    void delete(Long id);
}
