package be.pxl.researchproject.api.request;

import be.pxl.researchproject.domain.Client;
import be.pxl.researchproject.domain.Foal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateFoalRequestTest {
    private static final String FOAL_NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final double HEIGHT = 1.3;
    private static final String GENDER = "Mare";
    private static final String STALLION = "Dave";

    @Test
    public void createFoalRequestConstructorShouldSetEveryPropertyCorrectly(){
        CreateFoalRequest request = new CreateFoalRequest(FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION);

        assertEquals(FOAL_NAME, request.name());
        assertEquals(DATE_OF_BIRTH, request.dateOfBirth());
        assertEquals(HEIGHT, request.height());
        assertEquals(GENDER, request.gender());
        assertEquals(STALLION, request.stallion());
    }
}
