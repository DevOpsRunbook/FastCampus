package co.fastcampus.query.domain.service;

import co.fastcampus.query.application.dto.PhoneResponse;
import co.fastcampus.query.domain.converter.PhoneConverter;
import co.fastcampus.query.domain.exception.PhoneNotFoundException;
import co.fastcampus.query.domain.model.Phone;
import co.fastcampus.query.infrastructure.repository.PhoneRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FindPhoneService {

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private PhoneConverter phoneConverter;

    public PhoneResponse findByName(String name) throws PhoneNotFoundException {
        Phone phone = phoneRepository.findByName(name).orElseThrow(() -> new PhoneNotFoundException(name, "Phone not found"));
        log.info("Find phone: {}", phone);
        return phoneConverter.phoneToPhoneResponse(phone);
    }

    public void createPhone(Phone p) {
        log.info("Insert new phone: {}", p);
        phoneRepository.save(p);
    }
}
