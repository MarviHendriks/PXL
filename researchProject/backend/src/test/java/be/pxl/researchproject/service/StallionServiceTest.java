package be.pxl.researchproject.service;

import be.pxl.researchproject.exceptions.InvalidStallionCreatingException;
import be.pxl.researchproject.exceptions.StallionNotFoundException;
import be.pxl.researchproject.api.request.CreateStallionRequest;
import be.pxl.researchproject.api.request.UpdateStallionRequest;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.domain.Stallion;
import be.pxl.researchproject.repository.StallionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StallionServiceTest {
    private static final String NAME = "Kevin";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final String GENDER = "Stallion";
    private static final double HEIGHT = 1.3;
    private static final String COLORCODE = "Zwart";

    @Mock
    private StallionRepository stallionRepository;
    @Captor
    private ArgumentCaptor<Stallion> stallionArgumentCaptor;
    @InjectMocks
    private StallionService stallionService;

    private final Stallion stallion = new Stallion(NAME, DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);

    @Test
    public void findAllStallionsShouldReturnAListOfAllStallions(){
        Stallion stallion2 = new Stallion("Jimmy", DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);
        List<Stallion> stallions = new ArrayList<>();
        stallions.add(stallion);
        stallions.add(stallion2);

        when(stallionRepository.findAll()).thenReturn(stallions);

        List<StallionDTO> stallionDTOS = stallionService.findAllStallions();

        assertEquals(stallions.size(), stallionDTOS.size());
        assertEquals(stallions.get(0).getName(), stallionDTOS.get(0).getName());
        assertEquals(stallions.get(1).getName(), stallionDTOS.get(1).getName());
    }

    @Test
    public void createStallionsShouldThrowErrorIfStallionWithTheSameNameAlreadyExists(){
        CreateStallionRequest request = new CreateStallionRequest(NAME, DATE_OF_BIRTH.toString(), HEIGHT, COLORCODE);

        when(stallionRepository.findByName(NAME)).thenReturn(Optional.of(stallion));

        InvalidStallionCreatingException exception = assertThrows(InvalidStallionCreatingException.class,
                ()-> stallionService.createStallion(request));

        assertEquals("Stallion already exists", exception.getMessage());

    }

    @Test
    public void createStallionsShouldThrowErrorIfNotAllFieldsAreFilledIn(){
        CreateStallionRequest request1 = new CreateStallionRequest(null, null, 0, null);
        CreateStallionRequest request2 = new CreateStallionRequest("Timmy", null, 0, null);
        CreateStallionRequest request3 = new CreateStallionRequest("Limmy", DATE_OF_BIRTH.toString(), 0, null);
        CreateStallionRequest request4 = new CreateStallionRequest("Bimmy", DATE_OF_BIRTH.toString(), 1.2, null);

        InvalidStallionCreatingException exception1 = assertThrows(InvalidStallionCreatingException.class,
                ()-> stallionService.createStallion(request1));
        InvalidStallionCreatingException exception2 = assertThrows(InvalidStallionCreatingException.class,
                ()-> stallionService.createStallion(request2));
        InvalidStallionCreatingException exception3 = assertThrows(InvalidStallionCreatingException.class,
                ()-> stallionService.createStallion(request3));
        InvalidStallionCreatingException exception4 = assertThrows(InvalidStallionCreatingException.class,
                ()-> stallionService.createStallion(request4));

        assertEquals("Alle velden moeten ingevuld zijn", exception1.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception2.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception3.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception4.getMessage());
    }

    @Test
    public void createStallionsShouldCreateStallionWhenValidDataIsGiven(){
        CreateStallionRequest request = new CreateStallionRequest(NAME, DATE_OF_BIRTH.toString(), HEIGHT, COLORCODE);

        stallionService.createStallion(request);
        Mockito.verify(stallionRepository).save(stallionArgumentCaptor.capture());
        Stallion savedStallion = stallionArgumentCaptor.getValue();

        assertEquals(NAME, savedStallion.getName());
        assertEquals(DATE_OF_BIRTH, savedStallion.getDateOfBirth());
        assertEquals(HEIGHT, savedStallion.getHeight());
        assertEquals(COLORCODE, savedStallion.getColorCode());
    }

    @Test
    public void deleteStallionsShouldDeleteStallion(){
        doNothing().when(stallionRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> stallionService.deleteStallionById(1L));
    }

    @Test
    public void findStallionByIdShouldThrowErrorIfStallionDoesntExist(){
        when(stallionRepository.findById(anyLong())).thenReturn(Optional.empty());

        StallionNotFoundException exception = assertThrows(StallionNotFoundException.class,
                () -> stallionService.findStallionById(1L));

        assertEquals("De hengst is niet gevonden.", exception.getMessage());
    }

    @Test
    public void findStallionByIdShouldReturnStallionWhenValidIdIsGiven(){
        when(stallionRepository.findById(anyLong())).thenReturn(Optional.of(stallion));

        StallionDTO stallionDTO = stallionService.findStallionById(1L);

        assertEquals(NAME, stallionDTO.getName());
        assertEquals(DATE_OF_BIRTH, stallionDTO.getDateOfBirth());
        assertEquals(HEIGHT, stallionDTO.getHeight());
        assertEquals(GENDER.toLowerCase(), stallionDTO.getGender());
        assertEquals(COLORCODE, stallionDTO.getColorCode());
    }

    @Test
    public void updateStallionShouldThrowErrorWhenInvalidIdISGiven(){
        UpdateStallionRequest request = new UpdateStallionRequest(NAME, DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);
        when(stallionRepository.findById(anyLong())).thenReturn(Optional.empty());

        StallionNotFoundException exception = assertThrows(StallionNotFoundException.class,
                () -> stallionService.updateStallion(1L, request));

        assertEquals("De hengst is niet gevonden", exception.getMessage());
    }

    @Test
    public void updateStallionShouldUpdateStallionWhenValidDataIsGiven(){
        UpdateStallionRequest request = new UpdateStallionRequest("jimmy", DATE_OF_BIRTH, HEIGHT, GENDER, COLORCODE);

        when(stallionRepository.findById(anyLong())).thenReturn(Optional.of(stallion));
        stallionService.updateStallion(1L, request);

        Mockito.verify(stallionRepository).save(stallionArgumentCaptor.capture());
        Stallion savedStallion = stallionArgumentCaptor.getValue();

        assertEquals("jimmy", savedStallion.getName());
        assertEquals(DATE_OF_BIRTH, savedStallion.getDateOfBirth());
        assertEquals(HEIGHT, savedStallion.getHeight());
        assertEquals(COLORCODE, savedStallion.getColorCode());
    }

    @Test
    public void filterStallionsShouldGiveAListOfAllStallionsWithTheCorrectColorCode(){
        List<Stallion> stallions = new ArrayList<>();
        stallions.add(stallion);

        when(stallionRepository.findBycolorCodeStartingWith(COLORCODE)).thenReturn(stallions);

        Map<String, List<StallionDTO>> stallionDTOS = stallionService.filterStallions(COLORCODE);

        assertEquals(COLORCODE, stallionDTOS.get("stallions").get(0).getColorCode());
    }
}
