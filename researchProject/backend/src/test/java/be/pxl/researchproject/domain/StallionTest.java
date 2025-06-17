package be.pxl.researchproject.domain;

import be.pxl.researchproject.api.response.StallionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StallionTest {
    private static final Long ID = 1L;
    private static final String NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final String GENDER = "Stallion";
    private static final double HEIGHT = 1.3;
    private static final String COLORCODE = "Zwart";

    @Test
    public void stallionConstructorShouldSetEveryPropertyCorrectly(){
        Stallion stallion = new Stallion(NAME,
                DATE_OF_BIRTH,
                HEIGHT,
                GENDER,
                COLORCODE);

        assertEquals(NAME, stallion.getName());
        assertEquals(DATE_OF_BIRTH, stallion.getDateOfBirth());
        assertEquals(HEIGHT, stallion.getHeight());
        assertEquals(GENDER.toLowerCase(), stallion.getGender());
        assertEquals(COLORCODE, stallion.getColorCode());
    }

    @Test
    public void stallionGettersAndSettersShouldWorkCorrectly(){
        Stallion stallion = new Stallion();

        stallion.setId(ID);
        stallion.setName(NAME);
        stallion.setDateOfBirth(DATE_OF_BIRTH);
        stallion.setHeight(HEIGHT);
        stallion.setGender(GENDER);
        stallion.setColorCode(COLORCODE);

        assertEquals(ID, stallion.getId());
        assertEquals(NAME, stallion.getName());
        assertEquals(DATE_OF_BIRTH, stallion.getDateOfBirth());
        assertEquals(HEIGHT, stallion.getHeight());
        assertEquals(GENDER, stallion.getGender());
        assertEquals(COLORCODE, stallion.getColorCode());
    }
}
