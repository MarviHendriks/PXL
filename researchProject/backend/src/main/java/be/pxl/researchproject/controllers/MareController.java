package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.*;
import be.pxl.researchproject.api.response.CoveringDTO;
import be.pxl.researchproject.api.response.DiaryDTO;
import be.pxl.researchproject.api.response.HorseDTO;
import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.domain.Covering;
import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.service.MareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MareController {
    @Autowired
    private MareService mareService;
    @GetMapping("/mares")
    public List<MareDTO> getAllMares(){
        return mareService.findAllMares();
    }
    @GetMapping("/mares/search/{id}")
    public MareDTO getMareById(@PathVariable Long id){
        return mareService.findMareById(id);
    }
    @GetMapping("/mares/search")
    public List<MareDTO> getAllMaresByName(@RequestParam("name") String name){
        return mareService.findAllMaresByName(name);
    }

    @PostMapping("/mares/add")
    public ResponseEntity<MareDTO> createMare(@RequestBody CreateMareRequest request){
        MareDTO mare = mareService.createMare(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mare);
    }

    @GetMapping("/mares/name")
    public String horse(){
        return "horse";
    }

    @DeleteMapping("/mares/{id}")
    public ResponseEntity<?> deleteMare(@PathVariable Long id) {
        mareService.deleteMareById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mares/{id}/diary/add")
    public ResponseEntity<DiaryDTO> addEntryToDiary(@PathVariable("id") long id,
                                                      @RequestBody CreateDiaryEntryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(mareService.addEntryToDiary(id, request));
    }

    @GetMapping("/mares/{id}/diary")
    public ResponseEntity<List<DiaryDTO>> getMareDiary(@PathVariable("id") long id) {
        return ResponseEntity.ok(mareService.getAllDiaryEntries(id));
    }
    @PutMapping("/mares/diary/{id}/update")
    public ResponseEntity<Void> updateMareDiary(@PathVariable("id") long id, @RequestBody UpdateMareDiaryRequest request) {
        mareService.updateDiary(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/mares/diary/{id}/delete")
    public ResponseEntity<Void> deleteDiaryEntry(@PathVariable("id") long id){
        mareService.deleteDiaryEntry(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mares/coverDates/{id}/update")
    public ResponseEntity updateMareCoverDates(@PathVariable("id") long id, @RequestBody UpdateMareCoverdateRequest request) {
        mareService.updateCoverings(id, request);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/mares/{id}/coverDates/add")
    public ResponseEntity<CoveringDTO> addEntryToCoverDates(@PathVariable("id") long id,
                                                         @RequestBody CreateCoverdateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(mareService.addCovering(id, request));
    }

    @GetMapping("/mares/{id}/coverDates")
    public ResponseEntity<List<CoveringDTO>> getMareCoverDates(@PathVariable("id") long id) {
        mareService.getAllCoverings(id);
        return ResponseEntity.ok(mareService.getAllCoverings(id));
    }
    @DeleteMapping("/mares/coverDates/{id}/delete")
    public ResponseEntity<Void> deleteCoverdate(@PathVariable("id") long id){
        mareService.deleteCoverdate(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/mares/change/{id}")
    public ResponseEntity<Void> updateMare(@PathVariable("id") long id, @RequestBody UpdateMareRequest request) {
        mareService.updateMare(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mares/{id}/foals")
    public  ResponseEntity<Integer> getAmountOfFoals(@PathVariable Long id){
        return ResponseEntity.ok(mareService.amountOfFoals(id));
    }
    @GetMapping("/mares/filter")
    public Map<String, List<MareDTO>> filterMares(@RequestParam("isPregnant") String isPregnant, @RequestParam("dueDateFrom") String dueDateFrom, @RequestParam("dueDateTo") String dueDateTo) {
        return mareService.filterMares(isPregnant, dueDateFrom, dueDateTo);
    }

    @GetMapping("/mares/{id}/coverings")
    public  ResponseEntity<Integer> getAmountOfCoverings(@PathVariable Long id){
        return ResponseEntity.ok(mareService.amountOfCoverings(id));
    }

    //PDF
    @PostMapping("/mares/{id}/pdf")
    public ResponseEntity<String> uploadPDF(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        try {
            mareService.uploadPDF(id, file);
            return new ResponseEntity<>("File successvol geupload", HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>("File upload gefaald", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mares/{id}/pdf/read")
    public ResponseEntity<byte[]> getPDF(@PathVariable Long id){
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .body(mareService.getPDF(id));
    }

}
