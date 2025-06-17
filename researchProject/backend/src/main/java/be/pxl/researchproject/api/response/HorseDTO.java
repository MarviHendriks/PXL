package be.pxl.researchproject.api.response;

import java.time.LocalDate;

public abstract class HorseDTO {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private double height;
    private String gender;

    public HorseDTO() {
    }

    public HorseDTO(String name, LocalDate dateOfBirth, double height, String gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.gender = gender;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}