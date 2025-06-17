package be.pxl.researchproject.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FoalTest {
    private static final Long ID = 1L;
    private static final String FOAL_NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final double HEIGHT = 1.3;
    private static final String GENDER = "Mare";
    private static final String STALLION = "Dave";
    private static final Client CLIENT = new Client();

    @Test
    public void foalConstructorShouldSetEveryPropertyCorrectly(){
        Foal foal = new Foal(FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION);

        assertEquals(FOAL_NAME, foal.getName());
        assertEquals(DATE_OF_BIRTH, foal.getDateOfBirth());
        assertEquals(HEIGHT, foal.getHeight());
        assertEquals(GENDER, foal.getGender());
        assertEquals(STALLION, foal.getStallion());
        assertEquals(0, foal.getDewormings().size());
    }

    @Test
    public void GettersAndSettersShouldWorkCorrectly(){
        Foal foal = new Foal();

        foal.setName(FOAL_NAME);
        foal.setDateOfBirth(DATE_OF_BIRTH);
        foal.setId(ID);
        foal.setHeight(HEIGHT);
        foal.setGender(GENDER);
        foal.setStallion(STALLION);
        foal.setClient(CLIENT);

        assertEquals(ID, foal.getId());
        assertEquals(FOAL_NAME, foal.getName());
        assertEquals(DATE_OF_BIRTH, foal.getDateOfBirth());
        assertEquals(HEIGHT, foal.getHeight());
        assertEquals(GENDER, foal.getGender());
        assertEquals(STALLION, foal.getStallion());
        assertEquals(CLIENT, foal.getClient());
    }

}
