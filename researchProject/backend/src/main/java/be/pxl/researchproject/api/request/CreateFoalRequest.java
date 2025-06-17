package be.pxl.researchproject.api.request;

import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Stallion;

import java.time.LocalDate;

public record CreateFoalRequest (String name, LocalDate dateOfBirth, double height, String gender, String stallion){
}
