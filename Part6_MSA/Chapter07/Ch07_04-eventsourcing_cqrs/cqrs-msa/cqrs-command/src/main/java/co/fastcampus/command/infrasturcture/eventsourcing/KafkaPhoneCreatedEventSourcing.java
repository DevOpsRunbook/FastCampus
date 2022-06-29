package co.fastcampus.command.infrasturcture.eventsourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import co.fastcampus.command.domain.model.Phone;
import co.fastcampus.command.infrasturcture.eventsourcing.events.PhoneCreatedEvent;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class KafkaPhoneCreatedEventSourcing {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${message.topic.createPhone}")
    private String topicName;

    public PhoneCreatedEvent publicCreatePhoneEvent(Phone phone) throws JsonProcessingException {
        val id = UUID.randomUUID();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        val json = objectWriter.writeValueAsString(phone);
        log.info("Send json '{}' to topic {}", json, topicName);
        kafkaTemplate.send(topicName, json);
        return PhoneCreatedEvent.builder().uuid(id).phone(phone).build();
    }

}
