package exercise.exception;

public class UserExistsByEmailException extends RuntimeException{

    public UserExistsByEmailException(String message) {
        super(message);
    }
}
