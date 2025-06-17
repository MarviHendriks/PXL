package be.pxl.researchproject.api.request;

import java.time.LocalDate;

public record UpdateFoalRequest(String name, String dateOfBirth, double height) {
}
