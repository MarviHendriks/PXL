package be.pxl.researchproject.api.request;

import be.pxl.researchproject.domain.Foal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateStallionRequestTest {
    private static final String NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final String GENDER = "Stallion";
    private static final double HEIGHT = 1.3;
    private static final String COLORCODE = "Zwart";

    @Test
    public void updateStallionRequestConstructorShouldSetEveryPropertyCorrectly(){
        UpdateStallionRequest request = new UpdateStallionRequest(NAME, DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);
        assertEquals(NAME, request.getName());
        assertEquals(DATE_OF_BIRTH, request.getDateOfBirth());
        assertEquals(HEIGHT, request.getHeight());
        assertEquals(COLORCODE, request.getColorCode());
        assertEquals(GENDER, request.getGender());
    }

    @Test
    public void updateStallionRequestGetterAndSettersShouldWorkCorrectly(){
        UpdateStallionRequest request = new UpdateStallionRequest();

        request.setName(NAME);
        request.setDateOfBirth(DATE_OF_BIRTH.minusYears(3));
        request.setHeight(HEIGHT);
        request.setGender(GENDER);
        request.setColorCode(COLORCODE);

        assertEquals(NAME, request.getName());
        assertEquals(DATE_OF_BIRTH.minusYears(3), request.getDateOfBirth());
        assertEquals(HEIGHT, request.getHeight());
        assertEquals(GENDER, request.getGender());
        assertEquals(COLORCODE, request.getColorCode());
    }
}
