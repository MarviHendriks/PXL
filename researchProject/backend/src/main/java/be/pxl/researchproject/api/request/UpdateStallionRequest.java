package be.pxl.researchproject.api.request;

import java.time.LocalDate;
public class UpdateStallionRequest {

    private String name;
    private LocalDate dateOfBirth;
    private double height;
    private String gender;

    private String colorCode;

    public UpdateStallionRequest(String name, LocalDate dateOfBirth, double height, String gender, String colorCode) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.gender = gender;
        this.colorCode = colorCode;
    }

    public UpdateStallionRequest() {
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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
}
