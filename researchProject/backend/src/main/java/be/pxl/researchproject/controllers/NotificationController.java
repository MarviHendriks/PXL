package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.UserRequest;
import be.pxl.researchproject.api.response.AuthenticationDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.api.response.NotificationDTO;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Notification;
import be.pxl.researchproject.service.AuthenticationService;
import be.pxl.researchproject.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public List<NotificationDTO> getAllNotifications(){
        return notificationService.getAllNotifications();
    }
    @PostMapping("/notifications/{id}")
    public ResponseEntity<NotificationDTO> markNotification(@PathVariable Long id){
        NotificationDTO notif = notificationService.markNotification(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(notif);
    }
    @GetMapping("/notifications/unread")
    public int getUnreadNotifications(){
        return notificationService.getAmountOfUnreadNotifications();
    }
}
