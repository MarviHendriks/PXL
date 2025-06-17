package be.pxl.researchproject.api.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StallionDTOTest {
    private static final Long ID = 1L;
    private static final String NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final String GENDER = "Stallion";
    private static final double HEIGHT = 1.3;
    private static final String COLORCODE = "Zwart";

    @Test
    public void stallionDTOConstructorShouldSetEveryPropertyCorrectly(){
        StallionDTO stallionDTO = new StallionDTO(ID,
                NAME,
                DATE_OF_BIRTH,
                HEIGHT,
                GENDER,
                COLORCODE);

        assertEquals(ID, stallionDTO.getId());
        assertEquals(NAME, stallionDTO.getName());
        assertEquals(DATE_OF_BIRTH, stallionDTO.getDateOfBirth());
        assertEquals(HEIGHT, stallionDTO.getHeight());
        assertEquals(GENDER, stallionDTO.getGender());
        assertEquals(COLORCODE, stallionDTO.getColorCode());
    }

    @Test
    public void stallionDTOGettersAndSettersShouldWorkCorrectly(){
        StallionDTO stallionDTO = new StallionDTO(0L,
                null,
                null,
                0,
                null,
                null);

        stallionDTO.setId(ID);
        stallionDTO.setName(NAME);
        stallionDTO.setDateOfBirth(DATE_OF_BIRTH);
        stallionDTO.setHeight(HEIGHT);
        stallionDTO.setGender(GENDER);
        stallionDTO.setColorCode(COLORCODE);

        assertEquals(ID, stallionDTO.getId());
        assertEquals(NAME, stallionDTO.getName());
        assertEquals(DATE_OF_BIRTH, stallionDTO.getDateOfBirth());
        assertEquals(HEIGHT, stallionDTO.getHeight());
        assertEquals(GENDER, stallionDTO.getGender());
        assertEquals(COLORCODE, stallionDTO.getColorCode());
    }
}
