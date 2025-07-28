package com.lostway.eventnotificator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventKafkaListener {
    private static final String TOPIC_CHANGES = "event-changes";
    private static final String TOPIC_STATUS_CHANGES = "event-status-changes";


    @KafkaListener(
            topics = TOPIC_CHANGES,
            containerFactory = "eventKafkaListenerContainerFactory"
    )
    public void listen(EventChangeKafkaMessage message) {
        log.info("Kafka прислал сообщение ;) : {}", message);
    }

    @KafkaListener(
            topics = TOPIC_STATUS_CHANGES,
            containerFactory = "eventStatusKafkaListenerContainerFactory"
    )
    public void listen(EventStatusChangeKafkaMessage message) {
        log.info("Kafka прислал сообщение о смене статуса мероприятия: {}", message);
    }
}
