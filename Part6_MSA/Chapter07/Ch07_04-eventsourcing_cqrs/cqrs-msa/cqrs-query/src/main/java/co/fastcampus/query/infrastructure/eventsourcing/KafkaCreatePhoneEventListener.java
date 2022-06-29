package co.fastcampus.query.infrastructure.eventsourcing;

import com.google.gson.Gson;
import co.fastcampus.query.domain.model.Phone;
import co.fastcampus.query.domain.service.FindPhoneService;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Log4j2
@Component
public class KafkaCreatePhoneEventListener {

    @Autowired
    private FindPhoneService findPhoneService;
    private final CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(topics = "${message.topic.createPhone}")
    public void listen(ConsumerRecord<String, String> stringStringConsumerRecord) throws Exception {
        Phone phone = new Gson().fromJson(stringStringConsumerRecord.value(), Phone.class);
        findPhoneService.createPhone(phone);
        log.info("Insert phone {} in reader database", phone);
        latch.countDown();
    }
}
