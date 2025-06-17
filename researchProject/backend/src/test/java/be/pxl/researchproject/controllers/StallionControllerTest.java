package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.CreateStallionRequest;
import be.pxl.researchproject.api.request.UpdateFoalRequest;
import be.pxl.researchproject.api.request.UpdateStallionRequest;
import be.pxl.researchproject.api.response.FoalDTO;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.domain.Stallion;
import be.pxl.researchproject.service.StallionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StallionControllerTest {
    private static final Long ID = 1L;
    private static final String NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final String GENDER = "Stallion";
    private static final double HEIGHT = 1.3;
    private static final String COLORCODE = "Zwart";
    private final StallionDTO stallion = new StallionDTO(ID ,NAME, DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);
    @Mock
    private StallionService stallionService;
    @InjectMocks
    private StallionController stallionController;

    @Test
    public void getAllStallionsShouldReturnAllStallions(){
        List<StallionDTO> stallionDTOS = new ArrayList<>();
        stallionDTOS.add(stallion);

        when(stallionService.findAllStallions()).thenReturn(stallionDTOS);

        List<StallionDTO> response = stallionService.findAllStallions();

        assertEquals(stallionDTOS.size(), response.size());
    }

    @Test
    public void createStallionShouldCreateStallion(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateStallionRequest createRequest = new CreateStallionRequest(NAME, DATE_OF_BIRTH.toString(), HEIGHT, COLORCODE);
        StallionDTO newStallion = new StallionDTO(1L, NAME, DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);
        when(stallionService.createStallion(createRequest)).thenReturn(newStallion);

        ResponseEntity<StallionDTO> response = stallionController.createStallion(createRequest);
        assertEquals(201 ,response.getStatusCode().value());
        assertEquals(newStallion, response.getBody());
    }

    @Test
    public void deleteStallionByIdShouldDeleteStallion(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(stallionService).deleteStallionById(anyLong());
        ResponseEntity<?> response = stallionController.deleteStallion(1L);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void getStallionByIdShouldReturnStallion(){
        when(stallionService.findStallionById(anyLong())).thenReturn(stallion);

        StallionDTO response = stallionController.getStallionById(1L);

        assertEquals(ID, response.getId());
        assertEquals(COLORCODE, response.getColorCode());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
        assertEquals(HEIGHT, response.getHeight());
        assertEquals(GENDER, response.getGender());
    }

    @Test
    public void getAllStallionsByNameShouldReturnAllStallionsByName(){
        List<StallionDTO> stallionDTOS = new ArrayList<>();
        stallionDTOS.add(stallion);

        when(stallionController.getAllStallionsByName(any(String.class))).thenReturn(stallionDTOS);

        List<StallionDTO> response = stallionController.getAllStallionsByName(NAME);

        assertEquals(stallionDTOS.size(), response.size());
    }

    @Test
    public void updateStallionShouldUpdateStallion(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UpdateStallionRequest updateStallionRequest = new UpdateStallionRequest(NAME, DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);
        doNothing().when(stallionService).updateStallion(anyLong(), any(UpdateStallionRequest.class));

        ResponseEntity<Void> response = stallionController.updateStallion(1L, updateStallionRequest);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void filterStallionsShouldFilterStallions(){
        Map<String, List<StallionDTO>> filteredStallions = new HashMap<>();
        List<StallionDTO> stallionDTOS = new ArrayList<>();
        stallionDTOS.add(stallion);
        filteredStallions.put("stallions", stallionDTOS);

        when(stallionService.filterStallions(any(String.class))).thenReturn(filteredStallions);
        Map<String, List<StallionDTO>> response = stallionController.filterStallions(COLORCODE);

        assertEquals(filteredStallions.size(), response.size());
    }
}
