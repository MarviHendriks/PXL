package be.pxl.researchproject.api.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiaryDTOTest {
    //public record DiaryDTO(Long id, String entry, LocalDateTime date) {
    private static Long ID = 1L;
    private static String ENTRY = "A new diary entry";
    private static LocalDateTime DATE = LocalDateTime.now();
    @Test
    public void DiaryDTOConstructorShouldSetEveryPropertyCorrectly(){
        DiaryDTO diaryDTO = new DiaryDTO(ID, ENTRY, DATE);

        assertEquals(ID, diaryDTO.id());
        assertEquals(ENTRY, diaryDTO.entry());
        assertEquals(DATE, diaryDTO.date());
    }
}
