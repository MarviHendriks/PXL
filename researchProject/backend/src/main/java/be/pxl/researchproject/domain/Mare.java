package be.pxl.researchproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mare{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private double height;
    private String gender;
    private LocalDate dueDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "mare_id2")
    private List<Covering> coverings;
    private boolean isPregnant;
    private Integer daysPregnant;

    private Integer daysUntilDueDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "mare_id")
    private List<DiaryEntry> diary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "foal_id")
    private Foal foal;


    //PDF
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] pdfFile;

    public Mare() {
    }
    public Mare(String name, LocalDate dateOfBirth, double height, String gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.gender = gender.toLowerCase();
        this.isPregnant = false;
        this.diary = new ArrayList<>();
        this.coverings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public List<Covering> getCoverings() {
        return coverings;
    }

    public void setCoverings(List<Covering> coverings) {
        this.coverings = coverings;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public List<DiaryEntry> getDiary() {
        return diary;
    }

    public void setDiary(List<DiaryEntry> diary) {
        this.diary = diary;
    }

    public Integer getDaysPregnant() {
        return daysPregnant;
    }

    public void setDaysPregnant(Integer daysPregnant) {
        this.daysPregnant = daysPregnant;
    }

    public Integer getDaysUntilDueDate() {
        return daysUntilDueDate;
    }

    public void setDaysUntilDueDate(Integer daysUntilDueDate) {
        this.daysUntilDueDate = daysUntilDueDate;
    }

    public Foal getFoal() {
        return foal;
    }

    public void setFoal(Foal foal) {
        this.foal = foal;
    }

    //PDF
    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

    public byte[] getPdfFile() {
        return pdfFile;
    }
}
