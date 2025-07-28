package com.lostway.eventnotificator.kafka;

import com.lostway.eventnotificator.enums.EventStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventStatusChangeKafkaMessage {

    private Integer eventId;
    private EventStatus status;
    private LocalDateTime timestamp;
}
