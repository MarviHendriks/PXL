package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateDewormingRequestTest {
    private static final boolean ISDONE = true;

    @Test
    public void updateDewormingRequestConstructorShouldSetEveryPropertyCorrectly(){
        UpdateDewormingRequest request = new UpdateDewormingRequest(ISDONE);

        assertEquals(ISDONE, request.isDone());
    }
}
