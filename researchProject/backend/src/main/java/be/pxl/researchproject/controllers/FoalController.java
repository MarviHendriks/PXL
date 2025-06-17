package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.*;
import be.pxl.researchproject.api.response.FoalDTO;
import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.service.FoalService;
import be.pxl.researchproject.service.MareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoalController {
    @Autowired
    private FoalService foalService;

    @GetMapping("/foals/search/{id}")
    public FoalDTO getFoalByMareId(@PathVariable Long id){
        return foalService.findFoalByMareId(id);
    }

    @GetMapping("/foals/search/foal/{id}")
    public FoalDTO getFoalById(@PathVariable Long id){
        return foalService.findFoalById(id);
    }

    @DeleteMapping("/foals/{id}/delete")
    public ResponseEntity<Void> deleteFoal(@PathVariable Long id, @RequestBody DeleteFoalRequest request) {
        foalService.deleteFoalById(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/foals/{id}/update")
    public ResponseEntity<Void> updateFoal(@PathVariable("id") long id,
                                           @RequestBody UpdateFoalRequest request){
        foalService.updateFoal(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/foals/deworming/{id}/update")
    public ResponseEntity<Void> updateDeworming(@PathVariable("id") long id,
                                                @RequestBody UpdateDewormingRequest request){
        foalService.updateDeworming(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/foals/mare/{id}/add")
    public ResponseEntity<FoalDTO> addFoal(@PathVariable("id") long id,
                                                      @RequestBody CreateFoalRequest request){
        FoalDTO foal = foalService.addFoal(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(foal);
    }

    @GetMapping("/foals/{id}/clients")
    public  ResponseEntity<Boolean> getAmountOfClients(@PathVariable Long id){
        return ResponseEntity.ok(foalService.amountOfClients(id));
    }
}