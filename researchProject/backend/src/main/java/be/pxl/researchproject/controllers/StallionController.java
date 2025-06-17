package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.CreateStallionRequest;
import be.pxl.researchproject.api.request.UpdateStallionRequest;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.api.response.StallionDTO;
import be.pxl.researchproject.domain.Stallion;
import be.pxl.researchproject.service.StallionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class StallionController {
    @Autowired
    private StallionService stallionService;

    @GetMapping("/stallions")
    public List<StallionDTO> getAllStallions() {
        return stallionService.findAllStallions();
    }

    @PostMapping("/stallions/add")
    public ResponseEntity<StallionDTO> createStallion(@RequestBody CreateStallionRequest request){
        StallionDTO stallion = stallionService.createStallion(request);
        return new ResponseEntity<>(stallion, HttpStatus.CREATED);
    }

    @DeleteMapping("/stallions/{id}")
    public ResponseEntity<?> deleteStallion(@PathVariable Long id) {
        stallionService.deleteStallionById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/stallions/search/{id}")
    public StallionDTO getStallionById(@PathVariable Long id) {
        return stallionService.findStallionById(id);
    }

    @GetMapping("/stallions/search")
    public List<StallionDTO> getAllStallionsByName(@RequestParam("name") String name){
        return stallionService.findAllStallionsByName(name);
    }

    @PutMapping("/stallions/change/{id}")
    public ResponseEntity<Void> updateStallion(@PathVariable("id") long id, @RequestBody UpdateStallionRequest request) {
        stallionService.updateStallion(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stallions/filter")
    public Map<String, List<StallionDTO>> filterStallions(@RequestParam("colorCode") String colorCode) {
        return stallionService.filterStallions(colorCode);
    }

    //PDF
    @PostMapping("/stallions/{id}/pdf")
    public ResponseEntity<String> uploadPDF(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        try {
            stallionService.uploadPDF(id, file);
            return new ResponseEntity<>("File successvol geupload", HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>("File upload gefaald", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stallions/{id}/pdf/read")
    public ResponseEntity<byte[]> getPDF(@PathVariable Long id){
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .body(stallionService.getPDF(id));
    }

}