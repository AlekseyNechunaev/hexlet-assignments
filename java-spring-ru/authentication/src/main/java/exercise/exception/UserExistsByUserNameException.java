package exercise.exception;

public class UserExistsByUserNameException extends RuntimeException{

    public UserExistsByUserNameException(String message) {
        super(message);
    }
}
