package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.response.HorseDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.service.HorseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HorseControllerTest {
    private static final List<HorseDTO> MARE_LIST = List.of(new MareDTO(), new MareDTO());
    private static final List<HorseDTO> STALLION_LIST = List.of(new StallionDTO(1L,
                    "Jack", LocalDate.now(),
                    1.2,
                    "Stallion",
                    "Black"),
            new StallionDTO(2L,
                    "Jack", LocalDate.now(),
                    1.7,
                    "Stallion",
                    "Brown"));
    private static final  Map<String, List<HorseDTO>> HORSE_AND_MARE_LIST = new HashMap<>();

    @Mock
    private HorseService horseService;
    @InjectMocks
    private HorseController horseController;

    @Test
    public void getAllMaresAnStallionsShouldReturnAllMaresAndStallions() {
        HORSE_AND_MARE_LIST.put("mares", MARE_LIST);
        HORSE_AND_MARE_LIST.put("stallions", STALLION_LIST);

        when(horseService.getAllMaresAndStallions()).thenReturn(HORSE_AND_MARE_LIST);

        Map<String, List<HorseDTO>> response = horseController.getAllMaresAnStallions();

        assertEquals(2, response.size());
        assertTrue(response.containsKey("mares"));
        assertTrue(response.containsKey("stallions"));
        assertEquals(2, response.get("mares").size());
        assertEquals(2, response.get("stallions").size());
    }

    @Test
    public void getAllMaresAndStallionsByNameShouldReturnAllMaresAndStallionsWithSameName(){
        HORSE_AND_MARE_LIST.put("mares", MARE_LIST);
        HORSE_AND_MARE_LIST.put("stallions", STALLION_LIST);

        when(horseService.findAllMaresAndStallionsByName(anyString())).thenReturn(HORSE_AND_MARE_LIST);

        Map<String, List<HorseDTO>> response = horseController.getAllMaresAndStallionsByName("Jack");

        assertEquals(2, response.size());
        assertTrue(response.containsKey("mares"));
        assertTrue(response.containsKey("stallions"));
        assertEquals(2, response.get("mares").size());
        assertEquals(2, response.get("stallions").size());
    }
}
