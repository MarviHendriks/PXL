package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DeleteFoalRequestTest {
    private static final Long MAREID = 1L;

    @Test
    public void deleteClientRequestConstructorShouldSetEveryPropertyCorrectly(){
        DeleteFoalRequest request = new DeleteFoalRequest(MAREID);

        assertEquals(MAREID, request.mareId());
    }
}
