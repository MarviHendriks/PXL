package be.pxl.researchproject.service;

import be.pxl.researchproject.exceptions.InvalidMareCreatingException;
import be.pxl.researchproject.exceptions.MareNotFoundException;
import be.pxl.researchproject.api.request.*;
import be.pxl.researchproject.api.response.DiaryDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.api.response.*;
import be.pxl.researchproject.domain.Covering;
import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.repository.CoveringRepository;
import be.pxl.researchproject.repository.DiaryRepository;
import be.pxl.researchproject.repository.FoalRepository;
import be.pxl.researchproject.repository.MareRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MareService {
    @Autowired
    private MareRepository mareRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private FoalRepository foalRepository;

    @Autowired
    private CoveringRepository coveringRepository;

    public List<MareDTO> findAllMares() {
        return mareRepository.findAll().stream().map(mare -> {
            mare.setDaysUntilDueDate(calculateDaysUntilExpectedDelivery(mare.getDueDate()));
            mare.setDaysPregnant(getDaysPregnant(mare.getDaysUntilDueDate()));
            mareRepository.save(mare);
            return new MareDTO(mare.getId(), mare.getName(), mare.getHeight(), mare.getDateOfBirth(), mare.getGender(), mare.getDaysPregnant(), mare.getDueDate(), mare.getDaysUntilDueDate(), mare.getDiary(), mare.isPregnant());
        }).toList();
    }

    public List<MareDTO> findAllMaresByName(String name) {
        return mareRepository.findByNameStartingWith(name).stream().map(mare -> {
            mare.setDaysUntilDueDate(calculateDaysUntilExpectedDelivery(mare.getDueDate()));
            mare.setDaysPregnant(getDaysPregnant(mare.getDaysUntilDueDate()));
            return new MareDTO(mare.getId(), mare.getName(), mare.getHeight(), mare.getDateOfBirth(), mare.getGender(), mare.getDaysPregnant(), mare.getDueDate(), mare.getDaysUntilDueDate(), mare.getDiary(), mare.isPregnant());
        }).toList();
    }

    public MareDTO findMareById(long id) {
        Optional<Mare> result = mareRepository.findById(id);
        if (result.isPresent()) {
            Mare mare = result.get();
            mare.setDaysUntilDueDate(calculateDaysUntilExpectedDelivery(mare.getDueDate()));
            mare.setDaysPregnant(getDaysPregnant(mare.getDaysUntilDueDate()));
            return new MareDTO(mare.getId(), mare.getName(), mare.getHeight(), mare.getDateOfBirth(), mare.getGender(), mare.getDaysPregnant(), mare.getDueDate(), mare.getDaysUntilDueDate(), mare.getDiary(), mare.isPregnant());
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
    }

    public MareDTO createMare(CreateMareRequest request) {
        Optional<Mare> result = mareRepository.findByName(request.name());
        if (result.isPresent()) {
            throw new InvalidMareCreatingException("Mare already exists");
        }
        LocalDate dueDate = null;
        if (request.dueDate() != null) {
            dueDate = LocalDate.parse(request.dueDate());
        }
        LocalDate dateOfBirth = null;
        if (request.dateOfBirth() != null) {
            dateOfBirth = LocalDate.parse(request.dateOfBirth());
        }
        Mare mare = new Mare(request.name(), dateOfBirth, request.height(), "Mare");
        mare.setDaysUntilDueDate(calculateDaysUntilExpectedDelivery(dueDate));
        mare.setDaysPregnant(getDaysPregnant(mare.getDaysUntilDueDate()));

        // Create 1 entry in diary when creating a mare
        List<DiaryEntry> diary = new ArrayList<>();
        diary.add(new DiaryEntry("Merrie gecreëerd", LocalDateTime.now()));
        mare.setDiary(diary);
        Mare savedMare = mareRepository.save(mare);
        return convertMareToDTO(savedMare);
    }

    public Integer calculateDaysUntilExpectedDelivery(LocalDate dueDate) {
        if (dueDate != null) {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
        } else {
            return null;
        }
    }

    public Integer getDaysPregnant(Integer dueDate) {
        if (dueDate != null) {
            return 340 - dueDate;
        } else {
            return null;
        }
    }

    public LocalDate calculateDueDate(Mare mare) {
        if (mare.getDueDate() == null) {
            return mare.getCoverings().stream()
                    .sorted((w1, w2) -> w1.compareTo(w2))
                    .toList()
                    .getFirst().getCoverDate().plusDays(340);
        }
        return mare.getDueDate();
    }


    @Transactional
    public DiaryDTO addEntryToDiary(Long id, CreateDiaryEntryRequest request) {
        Optional<Mare> result = mareRepository.findById(id);

        if (!result.isPresent()) {
            throw new MareNotFoundException("De merrie is niet gevonden");
        } else {
            DiaryEntry entry = new DiaryEntry(request.entry(), LocalDateTime.now());
            Mare mare = result.get();
            mare.getDiary().add(entry);

            diaryRepository.save(entry);
            mareRepository.save(mare);

            DiaryEntry savedDiary = diaryRepository.save(entry);
            return new DiaryDTO(savedDiary.getId(), savedDiary.getEntry(), savedDiary.getEntryDate());
        }
    }

    public List<DiaryDTO> getAllDiaryEntries(Long id) {
        Optional<Mare> result = mareRepository.findById(id);

        if (!result.isPresent()) {
            throw new MareNotFoundException("De merrie is niet gevonden");
        } else {
            Mare mare = result.get();
            return mare.getDiary().stream()
                    .map(e -> new DiaryDTO(e.getId(), e.getEntry(), e.getEntryDate())).toList();
        }
    }

    public void updateDiary(Long id, UpdateMareDiaryRequest request) {
        Optional<DiaryEntry> result = diaryRepository.findById(id);

        if (!result.isPresent()) {
            throw new MareNotFoundException("Entry not found");
        } else {
            DiaryEntry entry = result.get();
            entry.setEntry(request.entry());
            diaryRepository.save(entry);
        }
    }

    public void deleteDiaryEntry(Long id) {
        diaryRepository.deleteById(id);
    }

    public void deleteCoverdate(Long id){
        coveringRepository.deleteById(id);
    }

    public void deleteMareById(Long id){
        mareRepository.deleteById(id);
    }

    public void updateMare(long mareId, UpdateMareRequest request) {
        Optional<Mare> optionalMare = mareRepository.findById(mareId);
        if (optionalMare.isPresent()) {
            Mare mare = optionalMare.get();
            mare.setName(request.name());
            mare.setGender(request.gender());
            mare.setPregnant(request.isPregnant());
            if (request.isPregnant()) {
                mare.setDueDate(calculateDueDate(mare));
            } else {
                mare.setDueDate(null);
            }
            LocalDate dateOfBirth = null;
            if (request.dateOfBirth() != null) {
                dateOfBirth = LocalDate.parse(request.dateOfBirth());
            }
            mare.setDateOfBirth(dateOfBirth);
            mare.setHeight(request.height());
            mareRepository.save(mare);
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
    }

    public List<CoveringDTO> getAllCoverings(Long id){
        Optional<Mare> result = mareRepository.findById(id);

        if (!result.isPresent()){
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
        else {
            Mare mare = result.get();
            return mare.getCoverings().stream()
                    .sorted((w1, w2) -> w1.compareTo(w2))
                    .map(e -> new CoveringDTO(e.getId(), e.getCoverDate()))
                    .toList();
        }
    }
    public CoveringDTO addCovering(Long id, CreateCoverdateRequest request){
        Optional<Mare> result = mareRepository.findById(id);
        Covering response = null;

        if (!result.isPresent()){
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
        else {
            Mare mare = result.get();

            if (mare.getDueDate() == null){
                Covering covering = new Covering(LocalDate.parse(request.entry()));
                mare.getCoverings().add(covering);
                response = coveringRepository.save(covering);
                mareRepository.save(mare);
            }
            else {
                throw new InvalidMareCreatingException("Kan geen dekdatums toevoegen wanneer een merrie al zwanger is");
            }
            return new CoveringDTO(response.getId(), response.getCoverDate());
        }
    }
    public void updateCoverings(Long id, UpdateMareCoverdateRequest coverings){
        Optional<Covering> result = coveringRepository.findById(id);

        if (!result.isPresent()){
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
        else {
            Covering covering = result.get();
            covering.setCoverDate(LocalDate.parse(coverings.entry()));
            Covering response = coveringRepository.save(covering);
        }
    }
    public Map<String, List<MareDTO>> filterMares(String isPregnant, String dueDateFrom, String dueDateTo) {
        LocalDate dueDateFromParsed = !dueDateFrom.equals("") ? LocalDate.parse(dueDateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        LocalDate dueDateToParsed = !dueDateTo.equals("") ? LocalDate.parse(dueDateTo, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        Boolean pregnant = !isPregnant.equals("") ? Boolean.parseBoolean(isPregnant) : null;
        List<MareDTO> mareDTOs =  mareRepository.filterMares(pregnant, dueDateFromParsed, dueDateToParsed).stream().map(mare -> {
            mare.setDaysUntilDueDate(calculateDaysUntilExpectedDelivery(mare.getDueDate()));
            mare.setDaysPregnant(getDaysPregnant(mare.getDaysUntilDueDate()));
            return new MareDTO(mare.getId(), mare.getName(), mare.getHeight(), mare.getDateOfBirth(), mare.getGender(), mare.getDaysPregnant(), mare.getDueDate(), mare.getDaysUntilDueDate() , mare.getDiary() ,mare.isPregnant());
        }).toList();
        return getStringListMap(mareDTOs);
    }
    @NotNull
    private Map<String, List<MareDTO>> getStringListMap(List<MareDTO> mareDTOs) {
        Map<String, List<MareDTO>> result = new HashMap<>();
        result.put("mares", mareDTOs.stream().map(dto -> (MareDTO) dto).collect(Collectors.toList()));
        return result;
    }

    public int amountOfFoals(Long id){
        Optional<Mare> optionalMare = mareRepository.findById(id);
        if (optionalMare.isPresent()) {
            Mare mare = optionalMare.get();
            return mare.getFoal() == null ? 0 : 1;
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
    }

    public int amountOfCoverings(Long id){
        Optional<Mare> optionalMare = mareRepository.findById(id);
        if (optionalMare.isPresent()) {
            Mare mare = optionalMare.get();
            return mare.getCoverings().isEmpty() ? 0 : 1;
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
    }

    //PDF
    public void uploadPDF(Long id, MultipartFile file) throws IOException {
        Optional<Mare> mareOptional = mareRepository.findById(id);
        if (mareOptional.isPresent()){
            Mare mare = mareOptional.get();
            mare.setPdfFile(file.getBytes());
            mareRepository.save(mare);
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden.");
        }
    }

    public byte[] getPDF(Long id){
        Optional<Mare> mareOptional = mareRepository.findById(id);
        if (mareOptional.isPresent() && mareOptional.get().getPdfFile() != null){
            return mareOptional.get().getPdfFile();
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden.");
        }
    }

    public MareDTO convertMareToDTO(Mare mare){
        return new MareDTO(mare.getId(),
                mare.getName(),
                mare.getHeight(),
                mare.getDateOfBirth(),
                mare.getGender(),
                mare.getDaysPregnant(),
                mare.getDueDate(),
                mare.getDaysUntilDueDate(),
                mare.getDiary(),
                mare.isPregnant());
    }
}