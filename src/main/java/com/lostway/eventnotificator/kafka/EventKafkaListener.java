package com.lostway.eventnotificator.kafka;

import com.lostway.eventdtos.EventChangeKafkaMessage;
import com.lostway.eventdtos.EventStatusChangeKafkaMessage;
import com.lostway.eventnotificator.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventKafkaListener {
    private static final String TOPIC_CHANGES = "event-changes";
    private static final String TOPIC_STATUS_CHANGES = "event-status-changes";
    private final NotificationService notificationService;


    @KafkaListener(
            topics = TOPIC_CHANGES,
            containerFactory = "eventKafkaListenerContainerFactory"
    )
    public void listen(EventChangeKafkaMessage message) {
        log.info("Kafka прислал сообщение ;) : {}", message);
        notificationService.addNotification(message);
    }

    @KafkaListener(
            topics = TOPIC_STATUS_CHANGES,
            containerFactory = "eventStatusKafkaListenerContainerFactory"
    )
    public void listen(EventStatusChangeKafkaMessage message) {
        log.info("Kafka прислал сообщение о смене статуса мероприятия: {}", message);
    }
}
