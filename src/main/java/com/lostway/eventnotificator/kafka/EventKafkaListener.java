package com.lostway.eventnotificator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventKafkaListener {

    @KafkaListener(
            topics = "event-changes",
            containerFactory = "eventKafkaListenerContainerFactory"
    )
    public void listen(EventChangeKafkaMessage message) {
        log.info("Kafka прислал сообщение ;) : {}", message);
    }
}
