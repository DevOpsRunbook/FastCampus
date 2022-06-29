package co.fastcampus.command.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import co.fastcampus.command.application.dto.CreatePhoneRequest;
import co.fastcampus.command.domain.converter.PhoneConverter;
import co.fastcampus.command.infrasturcture.eventsourcing.KafkaPhoneCreatedEventSourcing;
import co.fastcampus.command.infrasturcture.eventsourcing.events.PhoneCreatedEvent;
import co.fastcampus.command.infrasturcture.repository.PhoneRepository;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreateUserService {

    @Autowired
    private PhoneConverter phoneConverter;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private KafkaPhoneCreatedEventSourcing kafkaPhoneCreatedEventSourcing;

    public PhoneCreatedEvent create(CreatePhoneRequest request) {
        log.info("Creating new phone");
        val phone = phoneConverter.createPhoneRequestRequestToPhone(request);
        phoneRepository.save(phone);
        try {
            return kafkaPhoneCreatedEventSourcing.publicCreatePhoneEvent(phone);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
