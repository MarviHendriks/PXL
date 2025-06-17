package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.CoveringDTO;
import be.pxl.researchproject.api.response.DiaryDTO;
import be.pxl.researchproject.domain.Covering;
import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.exceptions.InvalidMareCreatingException;
import be.pxl.researchproject.exceptions.MareNotFoundException;
import be.pxl.researchproject.api.request.*;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.repository.CoveringRepository;
import be.pxl.researchproject.repository.DiaryRepository;
import be.pxl.researchproject.repository.FoalRepository;
import be.pxl.researchproject.repository.MareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MareServiceTest {
    private static final LocalDate DATE_OF_BIRTH = LocalDate.now();
    @Mock
    private MareRepository mareRepository;
    @Captor
    private ArgumentCaptor<Mare> mareArgumentCaptor;
    @InjectMocks
    private MareService mareService;
    private final Mare mare = new Mare("Layla", DATE_OF_BIRTH.minusYears(5), 1.7, "Mare");
    @Mock
    private Mare updatedMare;
    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private FoalRepository foalRepository;
    @Mock
    private CoveringRepository coveringRepository;
    @InjectMocks
    private MareService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllMaresReturnsCorrectMares() {
        when(mareRepository.findAll()).thenReturn(List.of(new Mare(), new Mare()));
        List<MareDTO> mares = mareService.findAllMares();
        assertEquals(2, mares.size());
    }

    @Test
    void findMareByIdReturnsCorrectMare() {
        Mare mare = new Mare();
        mare.setId(1L);
        when(mareRepository.findById(1L)).thenReturn(Optional.of(mare));
        MareDTO mareDTO = mareService.findMareById(1L);
        assertEquals(1L, mareDTO.getId());
    }

    @Test
    void findMareByIdThrowsExceptionWhenMareNotFound() {
        when(mareRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MareNotFoundException.class, () -> mareService.findMareById(1L));
    }

    @Test
    void createMareThrowsExceptionWhenMareAlreadyExists() {
        CreateMareRequest request = new CreateMareRequest("Existing Mare", LocalDate.now().toString(), 1.7, null);
        when(mareRepository.findByName("Existing Mare")).thenReturn(Optional.of(new Mare()));
        assertThrows(InvalidMareCreatingException.class, () -> mareService.createMare(request));
    }

    @Test
    void createMareReturnsCorrectMareWhenMareDoesNotExist() {
        String name = "New Mare";
        LocalDate date = LocalDate.now().minusYears(4);
        double height = 1.7;
        CreateMareRequest request = new CreateMareRequest(name, date.toString(), height, null);
        when(mareRepository.save(any(Mare.class))).thenReturn(updatedMare);

        mareService.createMare(request);
        Mockito.verify(mareRepository).save(mareArgumentCaptor.capture());
        Mare savedMare = mareArgumentCaptor.getValue();

        assertEquals(name, savedMare.getName());
        assertEquals(date.toString(), savedMare.getDateOfBirth().toString());
        assertEquals(height, savedMare.getHeight());
    }

    @Test
    void calculateDaysUntilExpectedDeliveryReturnsNullWhenDueDateIsNull() {
        Integer days = mareService.calculateDaysUntilExpectedDelivery(null);
        assertNull(days);
    }

    @Test
    void calculateDaysUntilExpectedDeliveryReturnsCorrectDaysWhenDueDateIsNotNull() {
        LocalDate dueDate = LocalDate.now().plusDays(10);
        Integer days = mareService.calculateDaysUntilExpectedDelivery(dueDate);
        assertEquals(10, days);
    }

    @Test
    void getDaysPregnantReturnsNullWhenDueDateIsNull() {
        Integer days = mareService.getDaysPregnant(null);
        assertNull(days);
    }

    @Test
    void getDaysPregnantReturnsCorrectDaysWhenDueDateIsNotNull() {
        Integer days = mareService.getDaysPregnant(10);
        assertEquals(330, days);
    }

    @Nested
    class WhenFindingAllMares {
        @BeforeEach
        void setup() {
        }

        @Test
        void returnsAllMares() {
            when(mareRepository.findAll()).thenReturn(List.of(mare, mare));
            List<MareDTO> mares = mareService.findAllMares();
            assertThat(mares).hasSize(2);
        }
    }

    @Nested
    class WhenFindingAllMaresByName {
        private final String NAME = "Layla";

        @BeforeEach
        void setup() {
        }

        @Test
        void returnsMaresWithMatchingName() {
            when(mareRepository.findByNameStartingWith(NAME)).thenReturn(List.of(mare));
            List<MareDTO> mares = mareService.findAllMaresByName(NAME);
            assertThat(mares).hasSize(1);
            assertThat(mares.get(0).getName()).isEqualTo(NAME);
        }
    }

    @Nested
    class WhenFindingMareById {
        private final long ID = 1;

        @BeforeEach
        void setup() {
        }

        @Test
        void returnsMareWhenIdExists() {
            mare.setId(ID);
            when(mareRepository.findById(ID)).thenReturn(Optional.of(mare));
            MareDTO mareDTO = mareService.findMareById(ID);
            assertNotNull(mareDTO);
            assertEquals(ID, mareDTO.getId());
        }

        @Test
        void throwsExceptionWhenIdDoesNotExist() {
            when(mareRepository.findById(ID)).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.findMareById(ID));
        }
    }

    @Nested
    class WhenCreatingMare {
        @Mock
        private CreateMareRequest request;

        @BeforeEach
        void setup() {
            request = new CreateMareRequest("New Mare", LocalDate.now().minusYears(4).toString(), 1.7, null);
        }

        @Test
        void throwsExceptionWhenMareAlreadyExists() {
            when(mareRepository.findByName(request.name())).thenReturn(Optional.of(mare));
            assertThrows(InvalidMareCreatingException.class, () -> mareService.createMare(request));
        }

        @Test
        void createsMareWhenItDoesNotExist() {
            when(mareRepository.findByName(request.name())).thenReturn(Optional.empty());
            mareService.createMare(request);
            verify(mareRepository).save(any(Mare.class));
        }
    }

    @Nested
    class WhenCalculatingDaysUntilExpectedDelivery {
        @BeforeEach
        void setup() {
        }
        @Test
        void returnsNullWhenDueDateIsNull() {
            Integer days = mareService.calculateDaysUntilExpectedDelivery(null);
            assertNull(days);
        }

        @Test
        void returnsCorrectDaysWhenDueDateIsNotNull() {
            LocalDate dueDate = LocalDate.now().plusDays(10);
            Integer days = mareService.calculateDaysUntilExpectedDelivery(dueDate);
            assertEquals(10, days);
        }
    }

    @Nested
    class WhenGettingDaysPregnant {
        @BeforeEach
        void setup() {
        }
        @Test
        void returnsNullWhenDueDateIsNull() {
            Integer days = mareService.getDaysPregnant(null);
            assertNull(days);
        }

        @Test
        void returnsCorrectDaysWhenDueDateIsNotNull() {
            Integer days = mareService.getDaysPregnant(10);
            assertEquals(330, days);
        }
    }

    @Nested
    class WhenCalculatingDueDate {
        @Mock
        private Mare mare;

        @BeforeEach
        void setup() {
        }

        @Test
        void returnsCorrectDueDateWhenCoveringsExist() {
            mare = new Mare();
            Covering covering = new Covering(LocalDate.now().minusDays(100));
            mare.setCoverings(List.of(covering));
            LocalDate dueDate = mareService.calculateDueDate(mare);
            assertEquals(covering.getCoverDate().plusDays(340), dueDate);
        }
    }


    @Nested
    class WhenAddingEntryToDiary {
        @Mock
        private CreateDiaryEntryRequest request;

        @BeforeEach
        void setup() {
            request = new CreateDiaryEntryRequest("New entry");
            mare.setId(1L);
        }

        @Test
        void addsEntryToDiary() {
            when(mareRepository.findById(1L)).thenReturn(Optional.of(mare));
            mareService.addEntryToDiary(1L, request);
            verify(diaryRepository, times(2)).save(any(DiaryEntry.class));
        }

        @Test
        void throwsExceptionWhenMareDoesNotExist() {
            when(mareRepository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.addEntryToDiary(1L, request));
        }
    }

    @Nested
    class WhenGettingAllDiaryEntries {
        @BeforeEach
        void setup() {
        }
        @Test
        void returnsAllDiaryEntries() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
            List<DiaryDTO> entries = mareService.getAllDiaryEntries(1L);
            assertThat(entries).isNotNull();
        }

        @Test
        void throwsExceptionWhenMareNotFound() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.getAllDiaryEntries(1L));
        }
    }

    @Nested
    class WhenUpdatingDiary {
        @Mock
        private UpdateMareDiaryRequest request;

        @BeforeEach
        void setup() {
            request = new UpdateMareDiaryRequest("Updated Entry");
        }

        @Test
        void updatesDiaryEntry() {
            DiaryEntry entry = new DiaryEntry("Original Entry", LocalDateTime.now());
            when(diaryRepository.findById(anyLong())).thenReturn(Optional.of(entry));

            mareService.updateDiary(1L, request);
            assertThat(entry.getEntry()).isEqualTo("Updated Entry");
        }

        @Test
        void throwsExceptionWhenEntryNotFound() {
            when(diaryRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.updateDiary(1L, request));
        }
    }

    @Nested
    class WhenDeletingDiaryEntry {
        @BeforeEach
        void setup() {
        }
        @Test
        void deletesDiaryEntry() {
            doNothing().when(diaryRepository).deleteById(anyLong());
            mareService.deleteDiaryEntry(1L);
            verify(diaryRepository).deleteById(1L);
        }
    }

    @Nested
    class WhenDeletingCoverdate {
        @BeforeEach
        void setup() {
        }
        @Test
        void deletesCoverdate() {
            doNothing().when(coveringRepository).deleteById(anyLong());
            mareService.deleteCoverdate(1L);
            verify(coveringRepository).deleteById(1L);
        }
    }

    @Nested
    class WhenDeletingMareById {
        @BeforeEach
        void setup() {
        }
        @Test
        void deletesMareById() {
            doNothing().when(mareRepository).deleteById(anyLong());
            mareService.deleteMareById(1L);
            verify(mareRepository).deleteById(1L);
        }
    }

    @Nested
    class WhenUpdatingMare {
        private final long MARE_ID = 53;
        @Mock
        private UpdateMareRequest request;

        @BeforeEach
        void setup() {
            request = new UpdateMareRequest("Updated Mare", "Mare" , false, LocalDate.now().minusYears(4).toString(), 1.8);
        }

        @Test
        void updatesMare() {
            Mare existingMare = new Mare("Original Mare", LocalDate.now().minusYears(5), 1.7, "Mare");
            when(mareRepository.findById(MARE_ID)).thenReturn(Optional.of(existingMare));

            mareService.updateMare(MARE_ID, request);

            assertThat(existingMare.getName()).isEqualTo("Updated Mare");
            assertThat(existingMare.getHeight()).isEqualTo(1.8);
        }

        @Test
        void throwsExceptionWhenMareNotFound() {
            when(mareRepository.findById(MARE_ID)).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.updateMare(MARE_ID, request));
        }
    }

    @Nested
    class WhenGettingAllCoverings {
        @BeforeEach
        void setup() {
        }
        @Test
        void returnsAllCoverings() {
            mare.setCoverings(List.of(new Covering(LocalDate.now().minusDays(100))));
            when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));

            List<CoveringDTO> coverings = mareService.getAllCoverings(1L);
            assertThat(coverings).hasSize(1);
        }

        @Test
        void throwsExceptionWhenMareNotFound() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.getAllCoverings(1L));
        }
    }

    @Nested
    class WhenAddingCovering {
        @Mock
        private CreateCoverdateRequest request;

        @BeforeEach
        void setup() {
            request = new CreateCoverdateRequest(LocalDate.now().toString());
        }

        @Test
        void addsCovering() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));

            mareService.addCovering(1L, request);
            verify(coveringRepository).save(any(Covering.class));
        }
        @Test
        void throwsExceptionWhenMareNotFound() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.addCovering(1L, request));
        }

        @Test
        void throwsExceptionWhenMareIsPregnant() {
            mare.setDueDate(LocalDate.now().plusMonths(11));
            when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
            assertThrows(InvalidMareCreatingException.class, () -> mareService.addCovering(1L, request));
        }
    }

    @Nested
    class WhenUpdatingCoverings {
        @Mock
        private UpdateMareCoverdateRequest coverings;

        @BeforeEach
        void setup() {
            coverings = new UpdateMareCoverdateRequest(LocalDate.now().plusDays(100).toString());
        }

        @Test
        void updatesCovering() {
            Covering existingCovering = new Covering(LocalDate.now().minusDays(100));
            when(coveringRepository.findById(anyLong())).thenReturn(Optional.of(existingCovering));

            mareService.updateCoverings(1L, coverings);
            assertThat(existingCovering.getCoverDate()).isEqualTo(LocalDate.now().plusDays(100));
        }

        @Test
        void throwsExceptionWhenCoveringNotFound() {
            when(coveringRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.updateCoverings(1L, coverings));
        }
    }

    @Nested
    class WhenFilteringMares {
        private final String IS_PREGNANT = "IS_PREGNANT";
        private final String DUE_DATE_FROM = "DUE_DATE_FROM";
        private final String DUE_DATE_TO = "DUE_DATE_TO";

        @BeforeEach
        void setup() {
        }

        @Test
        void returnsFilteredMares() {
            when(mareRepository.filterMares(any(), any(), any())).thenReturn(List.of(mare));

            Map<String, List<MareDTO>> result = mareService.filterMares("true", LocalDate.now().toString(), LocalDate.now().plusDays(100).toString());
            assertThat(result).isNotNull();
            assertThat(result.get("mares")).hasSize(1);
        }
    }

    @Nested
    class WhenAmountingOfFoals {
        @BeforeEach
        void setup() {
        }
        @Test
        void returnsFoalCount() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
            assertThat(mareService.amountOfFoals(1L)).isZero();
        }

        @Test
        void throwsExceptionWhenMareNotFound() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.amountOfFoals(1L));
        }
    }

    @Nested
    class WhenAmountingOfCoverings {
        @BeforeEach
        void setup() {
        }
        @Test
        void returnsCoveringCount() {
            mare.setCoverings(List.of(new Covering(LocalDate.now().minusDays(100))));
            when(mareRepository.findById(anyLong())).thenReturn(Optional.of(mare));
            assertThat(mareService.amountOfCoverings(1L)).isOne();
        }

        @Test
        void throwsExceptionWhenMareNotFound() {
            when(mareRepository.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(MareNotFoundException.class, () -> mareService.amountOfCoverings(1L));
        }
    }
}