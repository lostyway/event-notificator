package com.lostway.eventnotificator.controller.dto;

import com.lostway.eventdtos.EventStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventStatusNotification implements AbstractNotificationDto {

    @NotBlank
    private String message;

    @NotNull
    private Long eventId;

    @NotNull
    private EventStatus newStatus;

    @NotNull
    private LocalDateTime changeTime;
}
