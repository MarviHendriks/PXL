package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateMareRequestTest {
    private static final String NAME = "Tina";
    private static final String DATE_OF_BIRTH = LocalDate.now().toString();
    private static final double HEIGHT = 1.3;
    private static final String DUEDATE = LocalDate.now().plusMonths(10).toString();

    @Test
    public void createFoalRequestConstructorShouldSetEveryPropertyCorrectly(){
        CreateMareRequest request = new CreateMareRequest(NAME, DATE_OF_BIRTH, HEIGHT, DUEDATE);
        assertEquals(NAME, request.name());
        assertEquals(DATE_OF_BIRTH, request.dateOfBirth());
        assertEquals(HEIGHT, request.height());
        assertEquals(DUEDATE, request.dueDate());
    }
}
