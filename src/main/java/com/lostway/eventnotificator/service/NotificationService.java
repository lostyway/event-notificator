package com.lostway.eventnotificator.service;

import com.lostway.eventnotificator.kafka.EventChangeKafkaMessage;
import com.lostway.eventnotificator.repository.NotificationEntity;
import com.lostway.eventnotificator.repository.NotificationRepository;
import com.lostway.eventnotificator.utility.ClockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;

    /**
     * Добавление нотификации в БД.
     *
     * @param message сообщение, полученное от EventManager сервиса
     */
    public void addNotification(EventChangeKafkaMessage message) {
        for (Long userId : message.getUsers()) {
            NotificationEntity notification = NotificationEntity.builder()
                    .id(null)
                    .isRead(false)
                    .userId(userId)
                    .eventId(message.getEventId())
                    .message("Детали мероприятия были изменены.")
                    .createdAt(ClockUtil.getMoscowTimeNow())
                    .build();
            notificationRepository.save(notification);
            log.info("Создано уведомление для пользователя с ID: {}", userId);
        }
    }
}
