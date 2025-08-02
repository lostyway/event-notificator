package com.lostway.eventnotificator.controller;

import com.lostway.eventnotificator.controller.dto.EventChangeNotification;
import com.lostway.eventnotificator.controller.dto.EventStatusNotification;
import com.lostway.eventnotificator.controller.dto.MarkReadRequest;
import com.lostway.eventnotificator.security.CustomUserPrincipal;
import com.lostway.eventnotificator.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<EventChangeNotification>> getNotifications() {
        return ResponseEntity.ok(notificationService.findNotReadNotifications(getCurrentUserId()));
    }

    @PostMapping
    public ResponseEntity<Void> markNotificationsAsRead(@RequestBody MarkReadRequest eventIdOfNotifications) {
        notificationService.markNotificationsAsRead(eventIdOfNotifications.notificationIds(), getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserPrincipal.getId();
    }
}
