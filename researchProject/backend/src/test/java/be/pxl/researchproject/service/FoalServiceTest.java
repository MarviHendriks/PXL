package be.pxl.researchproject.service;

import be.pxl.researchproject.exceptions.FoalNotFoundException;
import be.pxl.researchproject.exceptions.InvalidFoalCreatingException;
import be.pxl.researchproject.exceptions.MareNotFoundException;
import be.pxl.researchproject.api.request.CreateFoalRequest;
import be.pxl.researchproject.api.request.DeleteFoalRequest;
import be.pxl.researchproject.api.request.UpdateDewormingRequest;
import be.pxl.researchproject.api.request.UpdateFoalRequest;
import be.pxl.researchproject.api.response.FoalDTO;
import be.pxl.researchproject.domain.*;
import be.pxl.researchproject.repository.CoveringRepository;
import be.pxl.researchproject.repository.DewormingRepository;
import be.pxl.researchproject.repository.FoalRepository;
import be.pxl.researchproject.repository.MareRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoalServiceTest {
    private static final String FOAL_NAME = "Simeran";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    private static final double HEIGHT = 1.3;
    private static final String GENDER = "Mare";
    private static final String STALLION = "Dave";

    // ik wil niet dat de test data gepushed wordt naar de werkelijke database
    // dus ik zal de repositories die ik voor deze testen moet gebruiken mocken
    // de repositories zullen nu de echte repositories simuleren maar pusht de data niet naar de database
    @Mock
    private FoalRepository foalRepository;
    @Mock
    private DewormingRepository dewormingRepository;
    @Mock
    private MareRepository mareRepository;
    @Mock
    private CoveringRepository coveringRepository;
    @Mock
    private Foal updatedFoal;

    @Mock
    private Foal addedFoal;
    @Mock
    private Deworming newDeworming;

    //data dat ik naar de fake database stuur wil ik kunnen testen
    // dit laat mij toe om de data die repository.save() terug stuurt te testen
    @Captor
    private ArgumentCaptor<Foal> foalArgumentCaptor;

    // ik injecteer nu alle mock repositries in mijn foalservice
    @InjectMocks
    private FoalService foalService;

    private final Foal foal = new Foal(FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION);
    private final Mare mare = new Mare("Layla", DATE_OF_BIRTH.minusYears(5), 1.7, "Mare");
    private final Deworming deworming = new Deworming("Vaccine 1", false, LocalDate.now());

    @Test
    public void throwsExceptionWhenFoalWithInvalidMareIdIsGiven(){
        //foalService.findFoalByMareId() maakt gebruik van de mareRepository.findById()
        // indien ik een niet bestaande id meegeef return null (Optional.empty())
        when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());
        FoalNotFoundException exception = assertThrows(FoalNotFoundException.class, () -> foalService.findFoalByMareId(1));
        assertEquals("Het veulen is niet gevonden", exception.getMessage());
    }

    @Test
    public void mareWithValidIdShouldReturnFoal(){
        foal.setId(2L);
        mare.setFoal(foal);
        when(mareRepository.findById(1L)).thenReturn(Optional.of(mare));
        FoalDTO foundFoal = foalService.findFoalByMareId(1);
        assertEquals(foal.getId(), foundFoal.getId());
        assertEquals(foal.getDateOfBirth(), foundFoal.getDateOfBirth());
        assertEquals(foal.getName(), foundFoal.getName());
        assertEquals(foal.getGender(), foundFoal.getGender());
        assertEquals(foal.getHeight(), foundFoal.getHeight());
        assertEquals(foal.getStallion(), foundFoal.getStallion());
    }

    @Test
    public void throwsErrorWhenYouTryToDeleteAFoalOfANonExistentMare(){
        when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());

        DeleteFoalRequest request = new DeleteFoalRequest(1L);
        MareNotFoundException exception = assertThrows(MareNotFoundException.class, () -> foalService.deleteFoalById(1L, request));

        assertEquals("De merrie is niet gevonden", exception.getMessage());
    }

    @Test
    public void throwsErrorWhenYouTryToDeleteANonExistingFoal(){
        mare.setFoal(null);
        when(mareRepository.findById(1L)).thenReturn(Optional.of(mare));

        DeleteFoalRequest request = new DeleteFoalRequest(1L);
        FoalNotFoundException exception = assertThrows(FoalNotFoundException.class, () -> foalService.deleteFoalById(1L, request));

        assertEquals("De merrie heeft geen veulen", exception.getMessage());
    }

    @Test
    public void deleteFoalWithCorrectFoalAndMareId(){
        foal.setId(1L);
        mare.setFoal(foal);

        when(mareRepository.findById(1L)).thenReturn(Optional.of(mare));
        DeleteFoalRequest request = new DeleteFoalRequest(1L);
        doNothing().when(foalRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> foalService.deleteFoalById(1L, request));
    }

    @Test
    public void throwsErrorWhenTryingToUpdateANonExistingFoal(){
        UpdateFoalRequest request = new UpdateFoalRequest(FOAL_NAME + "Eleven", DATE_OF_BIRTH.toString(), HEIGHT);
        when(foalRepository.findById(anyLong())).thenReturn(Optional.empty());
        FoalNotFoundException exception = assertThrows(FoalNotFoundException.class, ()-> foalService.updateFoal(1L, request));

        assertEquals("Het veulen is niet gevonden", exception.getMessage());
    }

    @Test
    public void throwsErrorWhenTryingToUpdateFoalWithEmptyFields(){
        UpdateFoalRequest request1 = new UpdateFoalRequest(null,null, 0.0);
        UpdateFoalRequest request2 = new UpdateFoalRequest("Timmy",null, 0.0);
        UpdateFoalRequest request3 = new UpdateFoalRequest("Timmy",DATE_OF_BIRTH.toString(), 0.0);

        when(foalRepository.findById(anyLong())).thenReturn(Optional.of(foal));
        FoalNotFoundException exception1 = assertThrows(FoalNotFoundException.class, ()-> foalService.updateFoal(1L, request1));
        FoalNotFoundException exception2 = assertThrows(FoalNotFoundException.class, ()-> foalService.updateFoal(1L, request2));
        FoalNotFoundException exception3 = assertThrows(FoalNotFoundException.class, ()-> foalService.updateFoal(1L, request3));


        assertEquals("Alle velden moeten ingvuld zijn", exception1.getMessage());
        assertEquals("Alle velden moeten ingvuld zijn", exception2.getMessage());
        assertEquals("Alle velden moeten ingvuld zijn", exception3.getMessage());
    }

    @Test
    public void updateFoalWhenCorrectDataIsGiven(){
        String newDate = LocalDate.now().minusDays(500).toString();
        String newName = "Jef";
        double newHeight = 1.89;

        UpdateFoalRequest request = new UpdateFoalRequest(newName, newDate, newHeight);

        when(foalRepository.findById(anyLong())).thenReturn(Optional.of(foal));
        when(foalRepository.save(any(Foal.class))).thenReturn(updatedFoal);
        when(dewormingRepository.save(any())).thenReturn(null);

        foalService.updateFoal(1L, request);
        Mockito.verify(foalRepository).save(foalArgumentCaptor.capture());
        Foal savedFoal = foalArgumentCaptor.getValue();

        assertEquals(newName, savedFoal.getName());
        assertEquals(newDate, savedFoal.getDateOfBirth().toString());
        assertEquals(newHeight, savedFoal.getHeight());
    }

    @Test
    public void throwsErrorWhenDewormingIdDoesntExist(){
        UpdateDewormingRequest request = new UpdateDewormingRequest(true);

        when(dewormingRepository.findById(anyLong())).thenReturn(Optional.empty());
        InvalidFoalCreatingException exception = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.updateDeworming(1L, request));

        assertEquals("Ontworming niet gevonden", exception.getMessage());
    }

    @Test
    public void throwsErrorWhenIsDoneFieldIsNotUpdated(){
        UpdateDewormingRequest request = new UpdateDewormingRequest(false);

        when(dewormingRepository.findById(anyLong())).thenReturn(Optional.of(deworming));
        InvalidFoalCreatingException exception = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.updateDeworming(1L, request));

        assertEquals("Ontworming niet aangepast", exception.getMessage());
    }

    @Test
    public void updateDewormingWhenIsDoneFieldHasChanged(){
        UpdateDewormingRequest request = new UpdateDewormingRequest(true);

        when(dewormingRepository.findById(anyLong())).thenReturn(Optional.of(deworming));

        assertDoesNotThrow(() -> foalService.updateDeworming(1L, request));
    }

    @Test
    public void amountOfClientsShouldThrowErrorWhenFoalIdDoesntExist(){
        when(foalRepository.findById(anyLong())).thenReturn(Optional.empty());
        FoalNotFoundException exception = assertThrows(FoalNotFoundException.class,() ->  foalService.amountOfClients(5L));

        assertEquals("Het veulen is niet gevonden", exception.getMessage());
    }

    @Test
    public void amountOfClientsShouldReturnFalseWhenFoalHasNoClient(){
        foal.setId(1L);
        when(foalRepository.findById(anyLong())).thenReturn(Optional.of(foal));

        boolean result = foalService.amountOfClients(1L);

        assertFalse(result);
    }

    @Test
    public void amountOfClientsShouldReturnTrueWhenFoalHasClient(){
        foal.setId(1L);
        foal.setClient(new Client("Jef", "jef@test.com", "0423457819", "adres 18", "adres17", "Januari"));
        when(foalRepository.findById(anyLong())).thenReturn(Optional.of(foal));

        boolean result = foalService.amountOfClients(1L);

        assertTrue(result);
    }

    @Test
    public void addFoalShouldThrowAErrorWhenMareIdDoesntExist(){
        when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());

        MareNotFoundException exception = assertThrows(MareNotFoundException.class, () -> foalService.addFoal(1L, null));

        assertEquals("De merrie is niet gevonden", exception.getMessage());
    }

    @Test
    public void addFoalShouldThrowErrorWhenMareAlreadyHasFoal(){
        mare.setFoal(new Foal());

        when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
        InvalidFoalCreatingException exception = assertThrows(InvalidFoalCreatingException.class, () -> foalService.addFoal(1L, null));

        assertEquals("Merrie heeft al een veulen", exception.getMessage());
    }

    @Test
    public void addFoalShouldThrowAErrorWhenNotAllRequestFieldsAreFilledIn(){
        CreateFoalRequest request1 = new CreateFoalRequest("jimmy", null, 0, null, null);
        CreateFoalRequest request2 = new CreateFoalRequest("Timmy", LocalDate.now(), 0, null, null);
        CreateFoalRequest request3 = new CreateFoalRequest("Wim", LocalDate.now(), 1.2, null, null);
        CreateFoalRequest request4 = new CreateFoalRequest("Wim", LocalDate.now(), 1.2, "Stallion", null);
        CreateFoalRequest request5 = new CreateFoalRequest("Wim", LocalDate.now(), 0, "Stallion", "Dave");

        when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));

        InvalidFoalCreatingException exception1 = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.addFoal(1L, request1));
        InvalidFoalCreatingException exception2 = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.addFoal(1L, request2));
        InvalidFoalCreatingException exception3 = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.addFoal(1L, request3));
        InvalidFoalCreatingException exception4 = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.addFoal(1L, request4));
        InvalidFoalCreatingException exception5 = assertThrows(InvalidFoalCreatingException.class, ()-> foalService.addFoal(1L, request5));

        assertEquals("Alle velden moeten ingevuld zijn", exception1.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception2.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception3.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception4.getMessage());
        assertEquals("Alle velden moeten ingevuld zijn", exception5.getMessage());
    }

    @Test
    public void addFoalShouldThrowErrorWhenMareHasNoCoveringDates(){
        CreateFoalRequest request = new CreateFoalRequest(FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION);

        when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
        InvalidFoalCreatingException exception = assertThrows(InvalidFoalCreatingException.class, () -> foalService.addFoal(1L, request));

        assertEquals("Kan geen veulen toevoegen aan een merrie zonder dekdatums", exception.getMessage());
    }

    @Test
    public void addFoalShouldCreateValidFoalWhenCorrectDataIsGiven(){
        CreateFoalRequest request = new CreateFoalRequest(FOAL_NAME, DATE_OF_BIRTH, HEIGHT, GENDER, STALLION);
        mare.getCoverings().add(new Covering(LocalDate.now().minusYears(1)));

        when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
        when(foalRepository.save(any(Foal.class))).thenReturn(addedFoal);
        when(dewormingRepository.save(any(Deworming.class))).thenReturn(newDeworming);
        doNothing().when(coveringRepository).deleteAllById(any());
        when(mareRepository.save(any(Mare.class))).thenReturn(mare);

        foalService.addFoal(1L, request);
        Mockito.verify(foalRepository).save(foalArgumentCaptor.capture());
        Foal savedFoal = foalArgumentCaptor.getValue();

        assertEquals(FOAL_NAME, savedFoal.getName());
        assertEquals(DATE_OF_BIRTH, savedFoal.getDateOfBirth());
        assertEquals(HEIGHT, savedFoal.getHeight());
        assertEquals(GENDER, savedFoal.getGender());
        assertEquals(STALLION, savedFoal.getStallion());
    }
}
