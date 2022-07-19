package co.fastcampus.kafka;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class OrderFailedException extends RuntimeException {

    public OrderFailedException(String msg) {
        super(msg);
    }
}
