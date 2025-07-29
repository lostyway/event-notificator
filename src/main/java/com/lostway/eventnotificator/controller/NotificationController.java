package com.lostway.eventnotificator.controller;

import com.lostway.eventnotificator.controller.dto.EventChangeNotification;
import com.lostway.eventnotificator.controller.dto.MarkReadRequest;
import com.lostway.eventnotificator.security.CustomUserPrincipal;
import com.lostway.eventnotificator.security.JWTUtil;
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
    private final JWTUtil jwtUtil;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<EventChangeNotification>> getNotifications() {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserPrincipal.getId();
        List<EventChangeNotification> eventChangeNotificationList = notificationService.findNotificationsById(userId);
        return ResponseEntity.ok(eventChangeNotificationList);
    }

    @PostMapping
    public ResponseEntity<Void> markNotificationsAsRead(@RequestBody MarkReadRequest request) {
        //TODO Пометить нотификации как прочитанные по request.getNotificationIds().
        // Также должна быть валидация что пользователь получал эту нотификацию.
        // ID нотификации должна быть по eventID либо по ID нотификации из БД.
        return ResponseEntity.noContent().build();
    }
}
