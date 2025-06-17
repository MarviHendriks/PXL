package be.pxl.researchproject.domain;

import be.pxl.researchproject.api.response.MareDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class MareTest {
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
    private static final List<Covering> COVERINGS = new ArrayList<>();
    private static final Foal FOAL = new Foal();

    @Test
    public void mareConstructorShouldSetEveryPropertyCorrectly(){
        Mare mare = new Mare(NAME,
                DATE_OF_BIRTH,
                HEIGHT,
                GENDER);

        assertEquals(NAME, mare.getName());
        assertEquals(HEIGHT, mare.getHeight());
        assertEquals(DATE_OF_BIRTH,  mare.getDateOfBirth());
        assertEquals(GENDER.toLowerCase(), mare.getGender());
        assertEquals(DIARY_ENTRIES, mare.getDiary());
        assertFalse(mare.isPregnant());
        assertEquals(0, mare.getCoverings().size());
    }

    @Test
    public void mareGettersAndSettersShouldWorkCorrectly(){
        Mare mare = new Mare();

        mare.setId(ID);
        mare.setName(NAME);
        mare.setHeight(HEIGHT);
        mare.setDateOfBirth(DATE_OF_BIRTH);
        mare.setGender(GENDER);
        mare.setDaysPregnant(DAYSPREGNANT);
        mare.setDueDate(DUEDATE);
        mare.setDaysUntilDueDate(DAYSUNTILDUEDATE);
        mare.setDiary(DIARY_ENTRIES);
        mare.setPregnant(ISPREGNANT);
        mare.setCoverings(COVERINGS);
        mare.setFoal(FOAL);

        assertEquals(ID, mare.getId());
        assertEquals(NAME, mare.getName());
        assertEquals(HEIGHT, mare.getHeight());
        assertEquals(DATE_OF_BIRTH,  mare.getDateOfBirth());
        assertEquals(GENDER, mare.getGender());
        assertEquals(DAYSPREGNANT, mare.getDaysPregnant());
        assertEquals(DUEDATE, mare.getDueDate());
        assertEquals(DAYSUNTILDUEDATE, mare.getDaysUntilDueDate());
        assertEquals(DIARY_ENTRIES, mare.getDiary());
        assertEquals(ISPREGNANT, mare.isPregnant());
        assertEquals(COVERINGS, mare.getCoverings());
        assertEquals(FOAL, mare.getFoal());
    }
}
