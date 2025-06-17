package be.pxl.researchproject.service;

import be.pxl.researchproject.exceptions.InvalidStallionCreatingException;
import be.pxl.researchproject.exceptions.StallionNotFoundException;
import be.pxl.researchproject.api.request.CreateStallionRequest;
import be.pxl.researchproject.api.request.UpdateStallionRequest;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Stallion;
import be.pxl.researchproject.repository.StallionRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StallionService {
    @Autowired
    private StallionRepository stallionRepository;

    public List<StallionDTO> findAllStallions(){
        return stallionRepository.findAll().stream().map(stallion -> {
            return new StallionDTO(stallion.getId(), stallion.getName(), stallion.getDateOfBirth(), stallion.getHeight(), stallion.getGender(), stallion.getColorCode());
        }).toList();
    }

    public StallionDTO createStallion(CreateStallionRequest request){
        Optional<Stallion> result = stallionRepository.findByName(request.name());
        if (result.isPresent()){
            throw new InvalidStallionCreatingException("Stallion already exists");
        } else if (request.name() == null ||
        request.height() == 0 ||
        request.dateOfBirth() == null ||
        request.colorCode() == null) {
            throw new InvalidStallionCreatingException("Alle velden moeten ingevuld zijn");
        } else {
            LocalDate dateOfBirth = LocalDate.parse(request.dateOfBirth());
            Stallion stallion = new Stallion(request.name(), dateOfBirth, request.height(), "Stallion" ,request.colorCode());
            Stallion savedStallion = stallionRepository.save(stallion);
            return new StallionDTO(savedStallion.getId(),
                    savedStallion.getName(),
                    savedStallion.getDateOfBirth(),
                    savedStallion.getHeight(),
                    savedStallion.getGender(),
                    savedStallion.getColorCode());
        }
    }
    public void deleteStallionById(Long id){
        stallionRepository.deleteById(id);
    }

    public List<StallionDTO> findAllStallionsByName(String name){
        return stallionRepository.findByNameStartingWith(name).stream().map(stallion -> {
            return new StallionDTO(stallion.getId(), stallion.getName(), stallion.getDateOfBirth(), stallion.getHeight(), stallion.getGender(),stallion.getColorCode());
        }).toList();
    }

    public StallionDTO findStallionById(long id){
        Optional<Stallion> result = stallionRepository.findById(id);
        if (result.isPresent()){
            Stallion stallion = result.get();
            return new StallionDTO(stallion.getId(), stallion.getName(), stallion.getDateOfBirth(), stallion.getHeight(), stallion.getGender(), stallion.getColorCode());
        } else {
            throw new StallionNotFoundException("De hengst is niet gevonden.");
        }
    }

    public void updateStallion(long mareId, UpdateStallionRequest request) {
        Optional<Stallion> optionalStallion = stallionRepository.findById(mareId);
        if (optionalStallion.isPresent()) {
            Stallion stallion = optionalStallion.get();
            stallion.setName(request.getName());
            stallion.setGender(request.getGender());
            stallion.setDateOfBirth(request.getDateOfBirth());
            stallion.setHeight(request.getHeight());
            stallion.setColorCode(request.getColorCode());
            stallionRepository.save(stallion);
        } else {
            throw new StallionNotFoundException("De hengst is niet gevonden");
        }
    }
    public Map<String, List<StallionDTO>> filterStallions(String colorCode) {
        List<StallionDTO> stallionDTOs =  stallionRepository.findBycolorCodeStartingWith(colorCode).stream().map(stallion -> {
            return new StallionDTO(stallion.getId(), stallion.getName(), stallion.getDateOfBirth(), stallion.getHeight(), stallion.getGender(), stallion.getColorCode());
        }).toList();
        return getStringListMap(stallionDTOs);
    }
    @NotNull
    private Map<String, List<StallionDTO>> getStringListMap(List<StallionDTO> stallionDTOS) {
        Map<String, List<StallionDTO>> result = new HashMap<>();
        result.put("stallions", stallionDTOS.stream().map(dto -> (StallionDTO) dto).collect(Collectors.toList()));
        return result;
    }

    //PDF
    public void uploadPDF(Long id, MultipartFile file) throws IOException {
        Optional<Stallion> stallionOptional = stallionRepository.findById(id);
        if (stallionOptional.isPresent()){
            Stallion stallion = stallionOptional.get();
            stallion.setPdfFile(file.getBytes());
            stallionRepository.save(stallion);
        } else {
            throw new StallionNotFoundException("De hengst is niet gevonden.");
        }
    }

    public byte[] getPDF(Long id){
        Optional<Stallion> stallionOptional = stallionRepository.findById(id);
        if (stallionOptional.isPresent() && stallionOptional.get().getPdfFile() != null){
            return stallionOptional.get().getPdfFile();
        } else {
            throw new StallionNotFoundException("De hengst is niet gevonden.");
        }
    }

}
