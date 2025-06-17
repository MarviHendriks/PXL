package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.NotificationDTO;
import be.pxl.researchproject.domain.Deworming;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Notification;
import be.pxl.researchproject.repository.MareRepository;
import be.pxl.researchproject.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private MareRepository mareRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void generateNotifications() {
        List<Mare> mares = new ArrayList<>();
        List<Foal> foals = new ArrayList<>();

        Mare mare1 = new Mare("merrie", LocalDate.now(), 1.1,"mare");
        mare1.setPregnant(true);
        mare1.setDueDate(LocalDate.now().plusDays(7));
        mare1.setDaysUntilDueDate(347);
        mares.add(mare1);

        Mare mare2 = new Mare("merrie2", LocalDate.now(), 1.1,"mare");
        mare2.setPregnant(true);
        mare2.setDueDate(LocalDate.now().minusDays(1));
        mare2.setDaysUntilDueDate(347);
        mares.add(mare2);

        Foal foal1 = new Foal("veulentje", LocalDate.now(), 1.1, "mare", "bob");
        foal1.setDewormings(List.of(new Deworming("Deworming 1", false, LocalDate.now())));
        foals.add(foal1);

        Foal foal2 = new Foal("veulentje2", LocalDate.now(), 1.1, "mare", "bob");
        foal2.setDewormings(List.of(new Deworming("Deworming 2", false, LocalDate.now())));
        foals.add(foal2);

        notificationService.generateNotifications(mares, foals);

        verify(notificationRepository, times(2)).save(any(Notification.class));
    }

    @Test
    void deleteAllNotifications() {
        notificationService.deleteAllNotifications();
        verify(notificationRepository, times(1)).deleteAll();
    }

    @Test
    void getAllNotifications() {
        Notification notification = new Notification();
        notification.setTitle("Test Notification");
        notification.setDate(LocalDate.now());
        notification.setUnread(true);
        Mare mare = new Mare("merrie", LocalDate.now(), 1.1,"mare");
        mare.setId(1L);
        Foal foal = new Foal("veulentje", LocalDate.now(), 1.1, "mare", "bob");
        foal.setId(2L);
        notification.setMare(mare);
        notification.setFoal(foal);

        List<Notification> notifications = List.of(notification);
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<NotificationDTO> result = notificationService.getAllNotifications();

        assertEquals(1, result.size());
        assertEquals(notification.getTitle(), result.getFirst().getTitle());
        assertEquals(notification.isUnread(), result.getFirst().isUnread());
        assertEquals(notification.getDate(), result.getFirst().getDate());
        assertNotNull(result.getFirst().getMareId());
        assertNotNull(result.getFirst().getFoalId());
    }

    @Test
    void markNotification() {
        Notification notification = new Notification();
        notification.setUnread(true);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        NotificationDTO result = notificationService.markNotification(1L);

        assertFalse(result.isUnread());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void getAmountOfUnreadNotifications() {

        Notification notification1 = new Notification();
        notification1.setUnread(true);
        Notification notification2 = new Notification();
        notification2.setUnread(false);

        List<Notification> notifications = List.of(notification1, notification2);
        when(notificationRepository.findAll()).thenReturn(notifications);

        int result = notificationService.getAmountOfUnreadNotifications();

        assertEquals(1, result);
    }
}