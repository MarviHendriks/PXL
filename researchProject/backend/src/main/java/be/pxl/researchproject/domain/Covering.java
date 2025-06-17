package be.pxl.researchproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Covering implements Comparable<Covering> {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate coverDate;

    public Covering() {

    }

    public Long getId() {
        return id;
    }


    public LocalDate getCoverDate() {
        return coverDate;
    }

    public void setCoverDate(LocalDate coverDate) {
        this.coverDate = coverDate;
    }

    public Covering(LocalDate coverDate) {
        this.coverDate = coverDate;
    }

    @Override
    public int compareTo(Covering other) {
        return other.getCoverDate().compareTo(this.coverDate);
    }
}
