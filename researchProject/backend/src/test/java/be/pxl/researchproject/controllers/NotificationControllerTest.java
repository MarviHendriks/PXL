package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.response.NotificationDTO;
import be.pxl.researchproject.domain.Notification;
import be.pxl.researchproject.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getAllNotifications_ReturnsListOfNotifications() {
        List<NotificationDTO> notifications = Collections.singletonList(new NotificationDTO());
        when(notificationService.getAllNotifications()).thenReturn(notifications);

        List<NotificationDTO> result = notificationController.getAllNotifications();

        assertEquals(notifications, result);
    }

    @Test
    void markNotification_ValidId_ReturnsCreatedResponseWithNotification() {
        Long id = 1L;
        NotificationDTO notification = new NotificationDTO();
        when(notificationService.markNotification(id)).thenReturn(notification);

        ResponseEntity<NotificationDTO> response = notificationController.markNotification(id);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(notification, response.getBody());
    }

    @Test
    void getUnreadNotifications_ReturnsAmountOfUnreadNotifications() {
        int unreadNotifications = 5;
        when(notificationService.getAmountOfUnreadNotifications()).thenReturn(unreadNotifications);

        int result = notificationController.getUnreadNotifications();

        assertEquals(unreadNotifications, result);
    }
}