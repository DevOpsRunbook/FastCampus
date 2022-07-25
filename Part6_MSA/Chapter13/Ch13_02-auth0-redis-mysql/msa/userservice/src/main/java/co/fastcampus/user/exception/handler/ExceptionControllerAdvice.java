package co.fastcampus.user.exception.handler;

import co.fastcampus.user.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDataNotFoundException(DataNotFoundException exception){
        return new ResponseEntity<>(
                ExceptionResponse
                        .builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Exception: Data Not Found")
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
