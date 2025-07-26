package com.lostway.eventnotificator.controller;

import com.lostway.eventnotificator.controller.dto.EventChangeNotification;
import com.lostway.eventnotificator.controller.dto.MarkReadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @GetMapping
    public ResponseEntity<List<EventChangeNotification>> getNotifications() {
        //TODO Извлечь пользователя из JWT и вернуть непрочитанные нотификации
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<Void> markNotificationsAsRead(@RequestBody MarkReadRequest request) {
        //TODO Пометить нотификации как прочитанные по request.getNotificationIds()
        return ResponseEntity.noContent().build();
    }
}
