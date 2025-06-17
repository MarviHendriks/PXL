package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateDiaryEntryRequestTest {
    private static final String ENTRY = LocalDate.now().toString();

    @Test
    public void createDiaryEntryRequestConstructorShouldSetEveryPropertyCorrectly(){
        CreateDiaryEntryRequest request = new CreateDiaryEntryRequest(ENTRY);
        assertEquals(ENTRY, request.entry());
    }
}
