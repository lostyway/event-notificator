package com.lostway.eventnotificator.controller.dto;

import com.lostway.eventdtos.FieldChange;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventChangeNotification implements AbstractNotificationDto {
    @NotNull
    private Long eventId;

    @NotNull
    private FieldChange<String> name;

    @NotNull
    private FieldChange<Integer> maxPlaces;

    @NotNull
    private FieldChange<LocalDateTime> date;

    @NotNull
    private FieldChange<Double> cost;

    @NotNull
    private FieldChange<Integer> duration;

    @NotNull
    private FieldChange<Long> locationId;
}
