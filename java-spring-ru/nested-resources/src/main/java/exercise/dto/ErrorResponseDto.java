package exercise.dto;

public class ErrorResponseDto {
    private final int code;

    private final String message;

    public ErrorResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
