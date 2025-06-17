package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.HorseDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.repository.MareRepository;
import be.pxl.researchproject.repository.StallionRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HorseService {

    @Autowired
    private MareRepository mareRepository;

    @Autowired
    private StallionRepository stallionRepository;

    @Autowired
    private MareService mareService;
    @Autowired
    private StallionService stallionService;

    public Map<String, List<HorseDTO>> getAllMaresAndStallions() {
        List<MareDTO> mareDTOs = mareService.findAllMares();
        List<StallionDTO> stallionDTOs = stallionService.findAllStallions();
        return getStringListMap(mareDTOs, stallionDTOs);
    }

    public Map<String, List<HorseDTO>> findAllMaresAndStallionsByName(String name) {
        List<MareDTO> mareDTOs = mareService.findAllMaresByName(name);
        List<StallionDTO> stallionDTOs = stallionService.findAllStallionsByName(name);
        return getStringListMap(mareDTOs, stallionDTOs);
    }

    @NotNull
    private Map<String, List<HorseDTO>> getStringListMap(List<MareDTO> mareDTOs, List<StallionDTO> stallionDTOs) {
        Map<String, List<HorseDTO>> result = new HashMap<>();
        result.put("mares", mareDTOs.stream().map(dto -> (HorseDTO) dto).collect(Collectors.toList()));
        result.put("stallions", stallionDTOs.stream().map(dto -> (HorseDTO) dto).collect(Collectors.toList()));
        return result;
    }


}
