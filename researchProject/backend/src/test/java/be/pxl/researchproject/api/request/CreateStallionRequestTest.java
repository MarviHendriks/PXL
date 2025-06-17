package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateStallionRequestTest {
    private static final String NAME = "Kevin";
    private static final String DATE_OF_BIRTH = LocalDate.now().toString();
    private static final double HEIGHT = 1.3;
    private static final String COLORCODE = "Zwart";

    @Test
    public void createStallionRequestConstructorShouldSetEveryPropertyCorrectly(){
        CreateStallionRequest request = new CreateStallionRequest(NAME, DATE_OF_BIRTH, HEIGHT, COLORCODE);
        assertEquals(NAME, request.name());
        assertEquals(DATE_OF_BIRTH, request.dateOfBirth());
        assertEquals(HEIGHT, request.height());
        assertEquals(COLORCODE, request.colorCode());
    }
}
