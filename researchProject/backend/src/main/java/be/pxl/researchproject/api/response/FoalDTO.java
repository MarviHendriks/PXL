package be.pxl.researchproject.api.response;

import be.pxl.researchproject.domain.Deworming;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Stallion;

import java.time.LocalDate;
import java.util.List;

public class FoalDTO extends HorseDTO {
    private Long id;
    private String stallion;

    private List<Deworming> dewormings;

    public FoalDTO(Long id, String name, LocalDate dateOfBirth, double height, String gender, String stallion, List<Deworming> dewormings) {
        super(name, dateOfBirth, height, gender);
        this.stallion = stallion;
        this.id = id;
        this.dewormings = dewormings;
    }


    public Long getId() {
        return id;
    }

    public String getStallion() {
        return stallion;
    }

    public List<Deworming> getDewormings() {
        return dewormings;
    }
}
