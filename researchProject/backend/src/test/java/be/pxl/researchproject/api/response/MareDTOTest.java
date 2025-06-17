package be.pxl.researchproject.api.response;

import be.pxl.researchproject.domain.Covering;
import be.pxl.researchproject.domain.DiaryEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MareDTOTest {
    private static final Long ID = 1L;
    private static final String NAME = "Tina";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final String GENDER = "Mare";
    private static final int DAYSPREGNANT = 142;
    private static final boolean ISPREGNANT = true;
    private static final double HEIGHT = 1.3;
    private static final LocalDate DUEDATE = LocalDate.now().plusDays(340);
    private static final int DAYSUNTILDUEDATE = 340;
    private static final List<DiaryEntry> DIARY_ENTRIES = new ArrayList<>();
    private static final List<LocalDate> COVERINGS = new ArrayList<>();

    @Test
    public void mareDTOConstructorShouldSetEveryPropertyCorrectly(){
        MareDTO mareDTO = new MareDTO(ID,
                NAME,
                HEIGHT,
                DATE_OF_BIRTH,
                GENDER,
                DAYSPREGNANT,
                DUEDATE,
                DAYSUNTILDUEDATE,
                DIARY_ENTRIES,
                ISPREGNANT);
        assertEquals(ID, mareDTO.getId());
        assertEquals(NAME, mareDTO.getName());
        assertEquals(HEIGHT, mareDTO.getHeight());
        assertEquals(DATE_OF_BIRTH,  mareDTO.getDateOfBirth());
        assertEquals(GENDER, mareDTO.getGender());
        assertEquals(DAYSPREGNANT, mareDTO.getDaysPregnant());
        assertEquals(DUEDATE.toString(), mareDTO.getDueDate());
        assertEquals(DAYSUNTILDUEDATE +"", mareDTO.getDaysUntilDueDate());
        assertEquals(DIARY_ENTRIES, mareDTO.getDiary());
        assertEquals(ISPREGNANT, mareDTO.isPregnant());
    }

    @Test
    public void mareDTOGettersAndSettersShouldWorkCorrectly(){
        MareDTO mareDTO = new MareDTO();

        mareDTO.setId(ID);
        mareDTO.setName(NAME);
        mareDTO.setHeight(HEIGHT);
        mareDTO.setDateOfBirth(DATE_OF_BIRTH);
        mareDTO.setGender(GENDER);
        mareDTO.setDaysPregnant(DAYSPREGNANT);
        mareDTO.setDueDate(DUEDATE);
        mareDTO.setDaysUntilDueDate(DAYSUNTILDUEDATE);
        mareDTO.setDiary(DIARY_ENTRIES);
        mareDTO.setPregnant(ISPREGNANT);
        mareDTO.setCoverings(COVERINGS);

        assertEquals(ID, mareDTO.getId());
        assertEquals(NAME, mareDTO.getName());
        assertEquals(HEIGHT, mareDTO.getHeight());
        assertEquals(DATE_OF_BIRTH,  mareDTO.getDateOfBirth());
        assertEquals(GENDER, mareDTO.getGender());
        assertEquals(DAYSPREGNANT, mareDTO.getDaysPregnant());
        assertEquals(DUEDATE.toString(), mareDTO.getDueDate());
        assertEquals(DAYSUNTILDUEDATE +"", mareDTO.getDaysUntilDueDate());
        assertEquals(DIARY_ENTRIES, mareDTO.getDiary());
        assertEquals(ISPREGNANT, mareDTO.isPregnant());
        assertEquals(ISPREGNANT, mareDTO.getPregnant());
        assertEquals(COVERINGS, mareDTO.getCoverings());
    }
}
