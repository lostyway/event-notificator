package com.lostway.eventnotificator.service;

import com.lostway.eventnotificator.controller.dto.EventChangeNotification;
import com.lostway.eventnotificator.kafka.EventChangeKafkaMessage;
import com.lostway.eventnotificator.mapper.ChangeLogMapper;
import com.lostway.eventnotificator.repository.ChangeLogEntity;
import com.lostway.eventnotificator.repository.ChangeLogRepository;
import com.lostway.eventnotificator.repository.NotificationEntity;
import com.lostway.eventnotificator.repository.NotificationRepository;
import com.lostway.eventnotificator.utility.ClockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ChangeLogRepository changeLogRepository;
    private final ChangeLogMapper changeLogMapper;

    /**
     * Добавление нотификации и ченджлога в БД.
     *
     * @param message сообщение, полученное от EventManager сервиса
     */
    public void addNotification(EventChangeKafkaMessage message) {
        ChangeLogEntity changeLogEntity = ChangeLogEntity.builder()
                .eventId(message.getEventId())
                .name(message.getName())
                .maxPlaces(message.getMaxPlaces())
                .date(message.getDate())
                .cost(message.getCost())
                .duration(message.getDuration())
                .locationId(message.getLocationId())
                .build();

        changeLogRepository.save(changeLogEntity);
        log.info("Создан changelog для изменения");

        for (Long userId : message.getUsers()) {
            NotificationEntity notification = NotificationEntity.builder()
                    .id(null)
                    .isRead(false)
                    .userId(userId)
                    .eventId(message.getEventId())
                    .createdAt(ClockUtil.getMoscowTimeNow())
                    .changelog(changeLogEntity)
                    .userToNotificate(userId)
                    .build();

            notificationRepository.save(notification);
            log.info("Создано уведомление для пользователя с ID: {}", userId);
        }
    }

    /**
     * Поиск непрочитанных уведомлений (changelog) для пользователя
     *
     * @param userId ID пользователя из базы данных
     * @return Список changelog уведомлений
     */

    @Transactional(readOnly = true)
    public List<EventChangeNotification> findNotReadNotifications(Long userId) {
        List<NotificationEntity> notificationList = notificationRepository.findByUserIdAndIsReadIsFalse(userId);

        List<ChangeLogEntity> changelogs = notificationList
                .stream()
                .map(NotificationEntity::getChangelog)
                .toList();

        return changeLogMapper.toEventChangeNotifications(changelogs);
    }

    public void markNotificationsAsRead(List<Long> notificationEventIds, Long userId) {
        notificationRepository.markIsReadByEventIdAndUserId(notificationEventIds, userId);
    }
}
