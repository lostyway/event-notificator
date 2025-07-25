package com.lostway.eventnotificator.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventChangeNotification(
        @NotNull Long eventId,
        @NotNull FieldChange<String> name,
        @NotNull FieldChange<Integer> maxPlaces,
        @NotNull FieldChange<LocalDateTime> date,
        @NotNull FieldChange<Long> cost,
        @NotNull FieldChange<Integer> duration,
        @NotNull FieldChange<Long> locationId
) {
}
