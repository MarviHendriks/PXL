package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateMareDiaryRequestTest {
    private static final String ENTRY = "I am writing a test";

    @Test
    public void updateMareDiaryRequestConstructorShouldSetEveryPropertyCorrectly(){
        UpdateMareDiaryRequest request = new UpdateMareDiaryRequest(ENTRY);
        assertEquals(ENTRY, request.entry());
    }
}
