package be.pxl.researchproject.controllers;
import be.pxl.researchproject.api.request.*;
import be.pxl.researchproject.api.response.CoveringDTO;
import be.pxl.researchproject.api.response.DiaryDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.domain.Covering;
import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.service.MareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import be.pxl.researchproject.domain.Mare;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MareControllerTest {

    @Mock
    private MareService mareService;

    @InjectMocks
    private MareController mareController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMaresReturnsCorrectMares() {
        when(mareService.findAllMares()).thenReturn(List.of(new MareDTO(), new MareDTO()));
        List<MareDTO> mares = mareController.getAllMares();
        assertEquals(2, mares.size());
    }

    @Test
    void getMareByIdReturnsCorrectMare() {
        MareDTO mareDTO = new MareDTO();
        mareDTO.setId(1L);
        when(mareService.findMareById(1L)).thenReturn(mareDTO);
        MareDTO result = mareController.getMareById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void createMareReturnsCorrectMare() {
        CreateMareRequest request = new CreateMareRequest("New Mare", LocalDate.now().toString(), 1.72, null);
        MareDTO mare = new MareDTO();
        mare.setName("New Mare");
        when(mareService.createMare(request)).thenReturn(mare);
        ResponseEntity<MareDTO> response = mareController.createMare(request);
        assertEquals("New Mare", response.getBody().getName());
    }

    @Test
    void deleteMareReturnsNoContent() {
        doNothing().when(mareService).deleteMareById(1L);
        ResponseEntity<?> response = mareController.deleteMare(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void getAmountOfFoalsReturnsCorrectAmount() {
        when(mareService.amountOfFoals(1L)).thenReturn(2);
        ResponseEntity<Integer> response = mareController.getAmountOfFoals(1L);
        assertEquals(2, response.getBody());
    }

    @Test
    void getAmountOfCoveringsReturnsCorrectAmount() {
        when(mareService.amountOfCoverings(1L)).thenReturn(3);
        ResponseEntity<Integer> response = mareController.getAmountOfCoverings(1L);
        assertEquals(3, response.getBody());
    }

    @Test
    void getAllMaresByNameReturnsCorrectMares() {
        String name = "Test";
        when(mareService.findAllMaresByName(name)).thenReturn(List.of(new MareDTO(), new MareDTO()));
        List<MareDTO> mares = mareController.getAllMaresByName(name);
        assertEquals(2, mares.size());
    }

    @Test
    void addEntryToDiaryReturnsCorrectEntry() {
        CreateDiaryEntryRequest request = new CreateDiaryEntryRequest(LocalDate.now().toString());
        DiaryDTO diaryEntry = new DiaryDTO(1L, "Test entry", LocalDateTime.now());
        when(mareService.addEntryToDiary(1L, request)).thenReturn(diaryEntry);
        ResponseEntity<DiaryDTO> response = mareController.addEntryToDiary(1L, request);
        assertEquals("Test entry", response.getBody().entry());
    }

    @Test
    void getMareDiaryReturnsCorrectEntries() {
        DiaryDTO diaryDTO1 = new DiaryDTO(1L, "test", LocalDateTime.now());
        DiaryDTO diaryDTO2 = new DiaryDTO(2L, "test2", LocalDateTime.now());
        when(mareService.getAllDiaryEntries(1L)).thenReturn(List.of(diaryDTO1, diaryDTO2));
        ResponseEntity<List<DiaryDTO>> response = mareController.getMareDiary(1L);
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateMareDiaryReturnsNoContent() {
        UpdateMareDiaryRequest request = new UpdateMareDiaryRequest( "Updated entry");
        doNothing().when(mareService).updateDiary(1L, request);
        ResponseEntity<Void> response = mareController.updateMareDiary(1L, request);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deleteDiaryEntryReturnsNoContent() {
        doNothing().when(mareService).deleteDiaryEntry(1L);
        ResponseEntity<Void> response = mareController.deleteDiaryEntry(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void updateMareCoverDatesReturnsNoContent() {
        UpdateMareCoverdateRequest request = new UpdateMareCoverdateRequest("test");
        doNothing().when(mareService).updateCoverings(1L, request);
        ResponseEntity<Void> response = mareController.updateMareCoverDates(1L, request);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void addEntryToCoverDatesReturnsCorrectEntry() {
        CreateCoverdateRequest request = new CreateCoverdateRequest(LocalDate.now().toString());
        CoveringDTO covering = new CoveringDTO(1L, LocalDate.now());
        when(mareService.addCovering(1L, request)).thenReturn(covering);
        ResponseEntity<CoveringDTO> response = mareController.addEntryToCoverDates(1L, request);
        assertEquals(LocalDate.now(), response.getBody().coveringDate());
    }

    @Test
    void getMareCoverDatesReturnsCorrectEntries() {
        CoveringDTO coveringDTO1 = new CoveringDTO(1L, LocalDate.now());
        CoveringDTO coveringDTO2 = new CoveringDTO(2L, LocalDate.now());
        when(mareService.getAllCoverings(1L)).thenReturn(List.of(coveringDTO1, coveringDTO2));
        ResponseEntity<List<CoveringDTO>> response = mareController.getMareCoverDates(1L);
        assertEquals(2, response.getBody().size());
    }

    @Test
    void deleteCoverdateReturnsNoContent() {
        doNothing().when(mareService).deleteCoverdate(1L);
        ResponseEntity<Void> response = mareController.deleteCoverdate(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void updateMareReturnsNoContent() {
        UpdateMareRequest request = new UpdateMareRequest("Updated Mare", "stallion", true, LocalDate.now().toString(), 1.2);
        doNothing().when(mareService).updateMare(1L, request);
        ResponseEntity<Void> response = mareController.updateMare(1L, request);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void filterMaresReturnsCorrectMares() {
        String isPregnant = "true";
        String dueDateFrom = LocalDate.now().toString();
        String dueDateTo = LocalDate.now().plusDays(10).toString();
        Map<String, List<MareDTO>> result = new HashMap<>();
        result.put("mares", List.of(new MareDTO(), new MareDTO()));
        when(mareService.filterMares(isPregnant, dueDateFrom, dueDateTo)).thenReturn(result);
        Map<String, List<MareDTO>> response = mareController.filterMares(isPregnant, dueDateFrom, dueDateTo);
        assertEquals(2, response.get("mares").size());
    }

    @Test
    void horseReturnsCorrectString() {
        String response = mareController.horse();
        assertEquals("horse", response);
    }
}