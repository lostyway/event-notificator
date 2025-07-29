package com.lostway.eventnotificator.scheduler;

import com.lostway.eventnotificator.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final NotificationService notificationService;
    @Value("${scheduler.days-to-delete}")
    private int daysToDelete;

    @Scheduled(cron = "${scheduler.cron}")
    @Transactional
    public void deleteReadedAndOldNotifications() {
        log.info("Началось удаление старых и прочитанных нотификаций");
        int count = notificationService.deleteReadedAndOldNotifications(daysToDelete);
        log.info("Нотификаций удалено: {}", count);
    }
}
