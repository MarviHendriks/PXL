package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateFoalRequestTest {
    private static final String FOAL_NAME = "Kevin";
    private static final String DATE_OF_BIRTH = LocalDate.now().toString();
    private static final double HEIGHT = 1.3;

    @Test
    public void updateFoalRequestConstructorShouldSetEveryPropertyCorrectly(){
        UpdateFoalRequest request = new UpdateFoalRequest(FOAL_NAME, DATE_OF_BIRTH, HEIGHT);

        assertEquals(FOAL_NAME, request.name());
        assertEquals(DATE_OF_BIRTH, request.dateOfBirth());
        assertEquals(HEIGHT, request.height());
    }
}
