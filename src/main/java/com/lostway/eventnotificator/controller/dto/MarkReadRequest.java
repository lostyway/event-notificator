package com.lostway.eventnotificator.controller.dto;

import java.util.List;

public record MarkReadRequest(
        List<Long> notificationIds
) {
}
