package be.pxl.researchproject.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class NotificationTest {

    @Test
    void getTitle() {
        Notification notification = new Notification();
        assertNull(notification.getTitle());
    }

    @Test
    void setTitle() {
        Notification notification = new Notification();
        notification.setTitle("Test Title");
        assertEquals("Test Title", notification.getTitle());
    }

    @Test
    void isUnread() {
        Notification notification = new Notification();
        assertFalse(notification.isUnread());
    }

    @Test
    void setUnread() {
        Notification notification = new Notification();
        notification.setUnread(true);
        assertTrue(notification.isUnread());
    }

    @Test
    void getDate() {
        Notification notification = new Notification();
        assertNull(notification.getDate());
    }

    @Test
    void setDate() {
        Notification notification = new Notification();
        notification.setDate(LocalDate.now());
        assertNotNull(notification.getDate());
    }

    @Test
    void getMare() {
        Notification notification = new Notification();
        assertNull(notification.getMare());
    }

    @Test
    void setMare() {
        Notification notification = new Notification();
        Mare mare = new Mare();
        notification.setMare(mare);
        assertEquals(mare, notification.getMare());
    }

    @Test
    void getFoal() {
        Notification notification = new Notification();
        assertNull(notification.getFoal());
    }

    @Test
    void setFoal() {
        Notification notification = new Notification();
        Foal foal = new Foal();
        notification.setFoal(foal);
        assertEquals(foal, notification.getFoal());
    }

    @Test
    void getId() {
        Notification notification = new Notification();
        assertNull(notification.getId());
    }
}