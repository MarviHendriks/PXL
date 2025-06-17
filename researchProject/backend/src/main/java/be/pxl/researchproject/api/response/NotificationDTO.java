package be.pxl.researchproject.api.response;
import be.pxl.researchproject.domain.DiaryEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationDTO {
    private Long id;
    private String title;
    private boolean unread;
    private LocalDate date;
    private Long mareId;
    private Long foalId;


    public NotificationDTO(Long id, String title, boolean unread, LocalDate date, Long mareId, Long foalId) {
        this.id = id;
        this.title = title;
        this.unread = unread;
        this.date = date;
        this.mareId = mareId;
        this.foalId = foalId;
    }

    public NotificationDTO() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUnread() {
        return unread;
    }
    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getMareId() {
        return mareId;
    }
    public void setMareId(Long mareId) {
        this.mareId = mareId;
    }
    public Long getFoalId() {
        return foalId;
    }
    public void setFoalId(Long foalId) {
        this.foalId = foalId;
    }


}