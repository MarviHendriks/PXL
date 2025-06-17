package be.pxl.researchproject.api.response;

import be.pxl.researchproject.api.request.CreateFoalRequest;
import be.pxl.researchproject.domain.Deworming;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FoalDTOTest {
    private static Long ID = 1L;
    private static final String FOAL_NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final double HEIGHT = 1.3;
    private static final String GENDER = "Mare";
    private static final String STALLION = "Dave";
    private static final List<Deworming> DEWORMINGS = new ArrayList<Deworming>(){{
        add(new Deworming("deworming 1", false, LocalDate.now()));
        add(new Deworming("deworming 2", false, LocalDate.now().plusMonths(1)));
        add(new Deworming("deworming 3", false, LocalDate.now().plusMonths(2)));
        add(new Deworming("deworming 4", false, LocalDate.now().plusMonths(3)));
    }};

    @Test
    public void createFoalDTOConstructorShouldSetEveryPropertyCorrectly(){
        FoalDTO foalDTO = new FoalDTO(ID ,FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION, DEWORMINGS);

        assertEquals(ID, foalDTO.getId());
        assertEquals(FOAL_NAME, foalDTO.getName());
        assertEquals(DATE_OF_BIRTH, foalDTO.getDateOfBirth());
        assertEquals(HEIGHT, foalDTO.getHeight());
        assertEquals(GENDER, foalDTO.getGender());
        assertEquals(STALLION, foalDTO.getStallion());
        assertEquals(DEWORMINGS, foalDTO.getDewormings());
    }
}
