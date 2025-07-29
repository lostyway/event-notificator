package com.lostway.eventnotificator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("select n from NotificationEntity n where n.userToNotificate = :userId and n.isRead = false")
    List<NotificationEntity> findByUserIdAndIsReadIsFalse(@Param("userId") Long userId);

    @Modifying
    @Query("update NotificationEntity n set n.isRead = true where n.eventId in :eventIds and n.userToNotificate = :userId")
    void markIsReadByEventIdAndUserId(
            @Param("eventIds") List<Long> eventIds,
            @Param("userId") Long userId
    );

    int deleteByIsReadIsTrueOrCreatedAtBefore(LocalDateTime timeAfterWhichRemove);
}
