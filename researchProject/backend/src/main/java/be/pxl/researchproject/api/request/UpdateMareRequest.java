package be.pxl.researchproject.api.request;

import java.time.LocalDate;
import java.util.List;
public record UpdateMareRequest(String name, String gender,boolean isPregnant,  String dateOfBirth, double height) {
}
