package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateMareCoverdateRequestTest {
    private static final String COVERDATE = LocalDate.now().toString();

    @Test
    public void updateMareCoverdateRequestConstructorShouldSetEveryPropertyCorrectly(){
        UpdateMareCoverdateRequest request = new UpdateMareCoverdateRequest(COVERDATE);
        assertEquals(COVERDATE, request.entry());
    }
}
