package com.lostway.eventnotificator.repository.entity;

import com.lostway.eventdtos.EventStatus;
import com.lostway.eventdtos.EventStatusChangeKafkaMessage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_notifications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventChangeStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "event_id")
    private Long eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private EventStatus newStatus;

    @Column(name = "change_time")
    private LocalDateTime changeTime;

    public static EventChangeStatusEntity getDefaultMessage(EventStatusChangeKafkaMessage message) {
        return EventChangeStatusEntity.builder()
                .id(null)
                .message("Статус мероприятия был изменен!")
                .eventId(message.getEventId())
                .newStatus(message.getStatus())
                .changeTime(message.getTimestamp())
                .build();
    }
}
