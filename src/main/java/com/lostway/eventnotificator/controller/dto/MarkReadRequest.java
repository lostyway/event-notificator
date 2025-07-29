package com.lostway.eventnotificator.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record MarkReadRequest(
        @NotNull(message = "Список идентификаторов не может быть null")
        @NotEmpty(message = "Список идентификаторов не может быть пустым")
        @Positive(message = "Идентификаторы должны быть положительными")
        List<Long> notificationIds
) {
}
