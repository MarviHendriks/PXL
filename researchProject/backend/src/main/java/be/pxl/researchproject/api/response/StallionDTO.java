package be.pxl.researchproject.api.response;

import java.time.LocalDate;

public class StallionDTO extends HorseDTO{
    private String colorCode;
    private Long id;

    public StallionDTO(Long id, String name, LocalDate dateOfBirth, double height, String gender, String colorCode){
        super(name, dateOfBirth, height, gender);
        this.id = id;
        this.colorCode = colorCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}