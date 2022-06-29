package co.fastcampus.query.domain.converter;

import co.fastcampus.query.application.dto.PhoneResponse;
import co.fastcampus.query.domain.model.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneConverter {

    public PhoneResponse phoneToPhoneResponse(Phone p) {
        return PhoneResponse.builder().color(p.getColor())
                .model(p.getModel())
                .name(p.getName())
                .price(p.getPrice())
                .build();
    }
}
