package be.pxl.researchproject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    private boolean unread;

    private LocalDate date;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "mare_id3")
    private Mare mare;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "foal_id3")
    private Foal foal;

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

    public Mare getMare() {
        return mare;
    }

    public void setMare(Mare mare) {
        this.mare = mare;
    }

    public Foal getFoal() {
        return foal;
    }

    public void setFoal(Foal foal) {
        this.foal = foal;
    }

    public Long getId() {
        return id;
    }
}
