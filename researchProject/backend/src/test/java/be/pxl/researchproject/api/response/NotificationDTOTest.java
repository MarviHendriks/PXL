package be.pxl.researchproject.api.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class NotificationDTOTest {

    @Test
    void getId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        assertNull(notificationDTO.getId());
    }

    @Test
    void setId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(1L);
        assertEquals(1L, notificationDTO.getId());
    }

    @Test
    void getTitle() {
        NotificationDTO notificationDTO = new NotificationDTO();
        assertNull(notificationDTO.getTitle());
    }

    @Test
    void setTitle() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTitle("Test Title");
        assertEquals("Test Title", notificationDTO.getTitle());
    }

    @Test
    void isUnread() {
        NotificationDTO notificationDTO = new NotificationDTO();
        assertFalse(notificationDTO.isUnread());
    }

    @Test
    void setUnread() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUnread(true);
        assertTrue(notificationDTO.isUnread());
    }

    @Test
    void getDate() {
        NotificationDTO notificationDTO = new NotificationDTO();
        assertNull(notificationDTO.getDate());
    }

    @Test
    void setDate() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setDate(LocalDate.now());
        assertEquals(LocalDate.now(), notificationDTO.getDate());
    }

    @Test
    void getMareId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        assertNull(notificationDTO.getMareId());
    }

    @Test
    void setMareId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setMareId(1L);
        assertEquals(1L, notificationDTO.getMareId());
    }

    @Test
    void getFoalId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        assertNull(notificationDTO.getFoalId());
    }

    @Test
    void setFoalId() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setFoalId(1L);
        assertEquals(1L, notificationDTO.getFoalId());
    }
}