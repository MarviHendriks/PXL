package be.pxl.researchproject.api.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DiaryDTO(Long id, String entry, LocalDateTime date) {
}
