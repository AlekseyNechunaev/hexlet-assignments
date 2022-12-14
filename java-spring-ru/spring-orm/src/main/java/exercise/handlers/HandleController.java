package exercise.handlers;

import exercise.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class HandleController {

    @ExceptionHandler(value = PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto sensorNotFoundException(PersonNotFoundException ex) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ex.getMessage());
    }
}
