package be.pxl.researchproject.api.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CoveringDTOTest {
    private static Long ID = 1L;
    private static LocalDate COVERINGDATE = LocalDate.now();
    @Test
    public void coveringDTOConstructorShouldSetEveryPropertyCorrectly(){
        CoveringDTO coveringDTO = new CoveringDTO(ID, COVERINGDATE);

        assertEquals(ID, coveringDTO.id());
        assertEquals(COVERINGDATE, coveringDTO.coveringDate());
    }
}
