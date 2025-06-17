package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.response.HorseDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.service.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class HorseController {
    @Autowired
    private HorseService horseService;

    @GetMapping("/horses")
    public Map<String, List<HorseDTO>> getAllMaresAnStallions(){
        return horseService.getAllMaresAndStallions();
    }

    @GetMapping("/horses/search")
    public Map<String, List<HorseDTO>> getAllMaresAndStallionsByName(@RequestParam("name") String name) {
        return horseService.findAllMaresAndStallionsByName(name);
    }
}
