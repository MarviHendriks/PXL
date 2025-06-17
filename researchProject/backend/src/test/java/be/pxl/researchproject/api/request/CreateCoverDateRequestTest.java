package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateCoverDateRequestTest {
    private static final String ENTRY = LocalDate.now().toString();

    @Test
    public void createCoverdateRequestConstructorShouldSetEveryPropertyCorrectly(){
        CreateCoverdateRequest request = new CreateCoverdateRequest(LocalDate.now().toString());

        assertEquals(ENTRY, request.entry());
    }
}
