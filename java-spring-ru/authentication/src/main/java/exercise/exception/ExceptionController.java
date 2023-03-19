package exercise.exception;

import exercise.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorDto userNameNotFoundException(UsernameNotFoundException ex) {
        return new ErrorDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserExistsByUserNameException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto userExistsByUserNameException(UserExistsByUserNameException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserExistsByEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto userExistsByEmailException(UserExistsByEmailException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
