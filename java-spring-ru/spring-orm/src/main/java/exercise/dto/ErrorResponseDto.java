package exercise.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

    private final int status;
    private final LocalDateTime time;

    private final String message;

    public ErrorResponseDto(int status, LocalDateTime time, String message) {
        this.status = status;
        this.time = time;
        this.message = message;
    }
}
