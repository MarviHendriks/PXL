package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.HorseDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Stallion;
import be.pxl.researchproject.repository.MareRepository;
import be.pxl.researchproject.repository.StallionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HorseServiceTest {
    private static final List<MareDTO> MARE_LIST = List.of(new MareDTO(), new MareDTO());
    private static final List<StallionDTO> STALLION_LIST = List.of(new StallionDTO(1L,
                    "Jack", LocalDate.now(),
                    1.2,
                    "Stallion",
                    "Black"),
            new StallionDTO(2L,
                    "Jack", LocalDate.now(),
                    1.7,
                    "Stallion",
                    "Brown"));

    @Mock
    private MareService mareService;
    @Mock
    private StallionService stallionService;
    @InjectMocks
    private HorseService horseService;


    @Test
    public void getAllMaresAndStallionsShouldReturnAllMaresAndStallions(){
        when(mareService.findAllMares()).thenReturn(MARE_LIST);
        when(stallionService.findAllStallions()).thenReturn(STALLION_LIST);

        Map<String, List<HorseDTO>> horseList = horseService.getAllMaresAndStallions();

        assertEquals(2, horseList.size());
        assertTrue(horseList.containsKey("mares"));
        assertTrue(horseList.containsKey("stallions"));
        assertEquals(2, horseList.get("mares").size());
        assertEquals(2, horseList.get("stallions").size());
    }

    @Test
    public void findAllMaresAndStallionsByName(){
        when(mareService.findAllMaresByName(anyString())).thenReturn(MARE_LIST);
        when(stallionService.findAllStallionsByName(anyString())).thenReturn(STALLION_LIST);

        Map<String, List<HorseDTO>> horseList = horseService.findAllMaresAndStallionsByName("Jack");

        assertEquals(2, horseList.size());
        assertTrue(horseList.containsKey("mares"));
        assertTrue(horseList.containsKey("stallions"));
        assertEquals(2, horseList.get("mares").size());
        assertEquals(2, horseList.get("stallions").size());
    }

}
