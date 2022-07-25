package co.fastcampus.user.exception.handler;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ExceptionResponse {

    private HttpStatus status;
    private String message;
}
