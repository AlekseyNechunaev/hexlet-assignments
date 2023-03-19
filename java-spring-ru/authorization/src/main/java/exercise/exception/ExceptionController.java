package exercise.exception;

import exercise.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto userNotFound(UserNotFoundException ex) {
        return new ErrorDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserExistsByEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto userExistsByEmail(UserExistsByEmailException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto resourceNotFound(ResourceNotFoundException ex) {
        return new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
