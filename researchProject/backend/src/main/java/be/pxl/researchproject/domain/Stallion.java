package be.pxl.researchproject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Stallion {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private double height;
    private String gender;
    private String colorCode;

    //PDF
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] pdfFile;

    public Stallion() {
    }

    public Stallion(String name, LocalDate dateOfBirth, double height, String gender, String colorCode) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.gender = gender.toLowerCase();
        this.colorCode = colorCode;
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    //PDF

    public byte[] getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }
}
