package be.pxl.researchproject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class DiaryEntry {
    @Id
    @GeneratedValue
    private Long id;
    @Lob //large object voor in de db!
    @Column(columnDefinition = "TEXT")
    private String entry;
    private LocalDateTime entryDate;

    public DiaryEntry(){

    }

    public DiaryEntry(String entry, LocalDateTime entryDate) {
        this.entry = entry;
        this.entryDate = entryDate;
    }

    public Long getId() {
        return id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
