package co.fastcampus.query.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FindPhoneException extends RuntimeException{
    public FindPhoneException() {
    }
}
