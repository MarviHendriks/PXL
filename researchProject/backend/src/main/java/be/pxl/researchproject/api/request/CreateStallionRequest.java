package be.pxl.researchproject.api.request;

import java.time.LocalDate;

public record CreateStallionRequest(String name, String dateOfBirth, double height, String colorCode) {
}
