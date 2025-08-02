package com.lostway.eventnotificator.service;

import com.lostway.eventdtos.EventChangeKafkaMessage;
import com.lostway.eventdtos.EventStatusChangeKafkaMessage;
import com.lostway.eventnotificator.controller.dto.EventChangeNotification;
import com.lostway.eventnotificator.mapper.ChangeLogMapper;
import com.lostway.eventnotificator.repository.EventChangeStatusRepository;
import com.lostway.eventnotificator.repository.entity.ChangeLogEntity;
import com.lostway.eventnotificator.repository.ChangeLogRepository;
import com.lostway.eventnotificator.repository.entity.EventChangeStatusEntity;
import com.lostway.eventnotificator.repository.entity.NotificationEntity;
import com.lostway.eventnotificator.repository.NotificationRepository;
import com.lostway.eventnotificator.utility.ClockUtil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

import static com.lostway.eventnotificator.repository.entity.ChangeLogEntity.getDefaultEntityFromMessage;
import static com.lostway.eventnotificator.repository.entity.NotificationEntity.getDefaultNotification;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Validated
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ChangeLogRepository changeLogRepository;
    private final ChangeLogMapper changeLogMapper;
    private final EventChangeStatusRepository eventChangeStatusRepository;

    /**
     * Добавление нотификации и ченджлога в БД.
     *
     * @param message сообщение, полученное от EventManager сервиса
     */
    public void addNotification(@NotNull EventChangeKafkaMessage message) {
        ChangeLogEntity changelog = getDefaultEntityFromMessage(message);

        changeLogRepository.save(changelog);
        log.info("Создан changelog для изменения");

        message.getUsers().stream()
                .filter(Objects::nonNull)
                .forEach(userId -> {
                    NotificationEntity notification = getDefaultNotification(userId, message.getEventId(), changelog);
                    notificationRepository.save(notification);
                    log.info("Создано уведомление для пользователя с ID: {}", userId);
                });
    }

    /**
     * Поиск непрочитанных уведомлений (changelog) для пользователя
     *
     * @param userId ID пользователя из базы данных
     * @return Список changelog уведомлений
     */

    @Transactional(readOnly = true)
    public List<EventChangeNotification> findNotReadNotifications(@NotNull Long userId) {
        List<NotificationEntity> notificationList = notificationRepository.findByUserIdAndIsReadIsFalse(userId);

        List<ChangeLogEntity> changelogs = notificationList.stream()
                .map(NotificationEntity::getChangelog)
                .toList();

        return changeLogMapper.toEventChangeNotifications(changelogs);
    }

    @Transactional(readOnly = true)
    public List<EventChangeNotification> findNotReadStatusNotifications(@NotNull Long userId) {
        List<NotificationEntity> notificationList = notificationRepository.findByUserIdAndIsReadIsFalse(userId);

        List<ChangeLogEntity> changelogs = notificationList.stream()
                .map(NotificationEntity::getChangelog)
                .toList();

        return changeLogMapper.toEventChangeNotifications(changelogs);
    }

    /**
     * Пометить выбранные нотификации пользователя как прочитанные
     *
     * @param notificationEventIds выбранные нотификации
     * @param userId               ID пользователя
     */

    public void markNotificationsAsRead(@NotEmpty List<Long> notificationEventIds, @NotNull Long userId) {
        notificationRepository.markIsReadByEventIdAndUserId(notificationEventIds, userId);
    }

    /**
     * Удаление старых и прочитанных нотификаций
     *
     * @return количество удаленных нотификаций
     */
    public int deleteReadedAndOldNotifications(int daysToDelete) {
        return notificationRepository.deleteByIsReadIsTrueOrCreatedAtBefore(ClockUtil.getMoscowTimeNow().minusDays(daysToDelete));
    }

    /**
     * Добавление нотификации о смене статуса мероприятия в БД.
     *
     * @param message сообщение, полученное от EventManager сервиса
     */

    public void addNotificationAboutChangeStatus(@NotNull EventStatusChangeKafkaMessage message) {
        EventChangeStatusEntity eventChangeStatus = EventChangeStatusEntity.getDefaultMessage(message);
        eventChangeStatusRepository.save(eventChangeStatus);
        log.info("Уведомление о смене статуса сохранено в БД");
    }
}
