package co.fastcampus.command.domain.converter;

import co.fastcampus.command.application.dto.CreatePhoneRequest;
import co.fastcampus.command.domain.model.Phone;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PhoneConverter {

    public Phone createPhoneRequestRequestToPhone(CreatePhoneRequest req) {
        return Phone.builder().color(req.getColor())
                .model(req.getModel())
                .name(req.getName())
                .price(req.getPrice())
                .creationDate(LocalDateTime.now().toString())
                .build();
    }
}
