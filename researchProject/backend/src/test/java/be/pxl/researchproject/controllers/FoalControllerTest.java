package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.CreateFoalRequest;
import be.pxl.researchproject.api.request.DeleteFoalRequest;
import be.pxl.researchproject.api.request.UpdateDewormingRequest;
import be.pxl.researchproject.api.request.UpdateFoalRequest;
import be.pxl.researchproject.api.response.FoalDTO;
import be.pxl.researchproject.domain.Deworming;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.service.FoalService;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoalControllerTest {
    private static final Long ID = 1L;
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
    @Mock
    private FoalService foalService;
    @InjectMocks
    private FoalController foalController;
    private final FoalDTO foal = new FoalDTO(ID, FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION, DEWORMINGS);

    @Test
    public void getFoalByMareIdShouldReturnFoal(){
        when(foalService.findFoalByMareId(anyLong())).thenReturn(foal);

        FoalDTO response = foalController.getFoalByMareId(1L);

        assertEquals(ID, response.getId());
        assertEquals(FOAL_NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
        assertEquals(HEIGHT, response.getHeight());
        assertEquals(GENDER, response.getGender());
        assertEquals(STALLION, response.getStallion());
        assertEquals(DEWORMINGS, response.getDewormings());
    }

    @Test
    public void deleteFoalByIdShouldDeleteFoal(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(foalService).deleteFoalById(anyLong(), any(DeleteFoalRequest.class));
        DeleteFoalRequest deleteFoalRequest = new DeleteFoalRequest(1L);
        ResponseEntity<Void> response = foalController.deleteFoal(1L, deleteFoalRequest);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void updateFoalShouldUpdateFoal(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UpdateFoalRequest updateFoalRequest = new UpdateFoalRequest(FOAL_NAME, DATE_OF_BIRTH.toString(), HEIGHT);
        doNothing().when(foalService).updateFoal(anyLong(), any(UpdateFoalRequest.class));
        ResponseEntity<Void> response = foalController.updateFoal(1L, updateFoalRequest);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void updateDewormingShouldUpdateDeworming(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UpdateDewormingRequest updateDewormingRequest = new UpdateDewormingRequest(true);
        doNothing().when(foalService).updateDeworming(anyLong(), any(UpdateDewormingRequest.class));
        ResponseEntity<Void> response = foalController.updateDeworming(1L, updateDewormingRequest);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void addFoalShouldAddFoal(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateFoalRequest createFoalRequest = new CreateFoalRequest(FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION);
        FoalDTO newFoal = new FoalDTO(1L, FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION, DEWORMINGS);
        when(foalService.addFoal(anyLong(), any(CreateFoalRequest.class))).thenReturn(newFoal);
        ResponseEntity<FoalDTO> response = foalController.addFoal(1L, createFoalRequest);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(newFoal, response.getBody());
    }

    @Test
    public void getAmountOfClientsShouldReturnTrueIfFoalHasClient(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(foalService.amountOfClients(anyLong())).thenReturn(true);
        ResponseEntity<Boolean> response = foalController.getAmountOfClients(1L);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody());
    }

    @Test
    public void getAmountOfClientsShouldReturnFalseIfFoalHasNoClient(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(foalService.amountOfClients(anyLong())).thenReturn(false);
        ResponseEntity<Boolean> response = foalController.getAmountOfClients(1L);

        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody());
    }
}
