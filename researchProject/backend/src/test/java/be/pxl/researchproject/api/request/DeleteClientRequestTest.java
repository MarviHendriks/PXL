package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DeleteClientRequestTest {
    private static final Long FOALID = 1L;

    @Test
    public void deleteClientRequestConstructorShouldSetEveryPropertyCorrectly(){
        DeleteClientRequest request = new DeleteClientRequest(FOALID);

        assertEquals(FOALID, request.foalId());
    }
}
