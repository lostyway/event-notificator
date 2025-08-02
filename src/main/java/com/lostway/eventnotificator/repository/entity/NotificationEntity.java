package com.lostway.eventnotificator.repository.entity;

import com.lostway.eventnotificator.utility.ClockUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changelog", nullable = false)
    private ChangeLogEntity changelog;

    @Column(name = "user_to_notificate", nullable = false)
    private Long userToNotificate;

    public static NotificationEntity getDefaultNotification(Long userId, Long eventId, ChangeLogEntity changelog) {
        return NotificationEntity.builder()
                .id(null)
                .isRead(false)
                .userId(userId)
                .eventId(eventId)
                .createdAt(ClockUtil.getMoscowTimeNow())
                .changelog(changelog)
                .userToNotificate(userId)
                .build();
    }
}
