package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateMareRequestTest {
    private static final String NAME = "Tina";
    private static final String DATE_OF_BIRTH = LocalDate.now().toString();
    private static final String GENDER = "Mare";
    private static final boolean ISPREGNANT = true;
    private static final double HEIGHT = 1.3;

    @Test
    public void updateMareRequestConstructorShouldSetEveryPropertyCorrectly(){
        UpdateMareRequest request = new UpdateMareRequest(NAME, GENDER, ISPREGNANT, DATE_OF_BIRTH, HEIGHT);
        assertEquals(NAME, request.name());
        assertEquals(GENDER, request.gender());
        assertEquals(ISPREGNANT, request.isPregnant());
        assertEquals(DATE_OF_BIRTH, request.dateOfBirth());
        assertEquals(HEIGHT, request.height());
    }
}
