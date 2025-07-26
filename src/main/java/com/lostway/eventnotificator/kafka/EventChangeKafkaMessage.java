package com.lostway.eventnotificator.kafka;

import com.lostway.eventnotificator.controller.dto.FieldChange;
import com.lostway.eventnotificator.enums.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventChangeKafkaMessage {

    private Long eventId;

    private final List<Long> users;

    private final Long changedById;

    private FieldChange<String> name;
    private FieldChange<Integer> maxPlaces;
    private FieldChange<LocalDateTime> date;
    private FieldChange<Integer> cost;
    private FieldChange<Integer> duration;
    private FieldChange<Integer> locationId;
    private FieldChange<EventStatus> status;

}
