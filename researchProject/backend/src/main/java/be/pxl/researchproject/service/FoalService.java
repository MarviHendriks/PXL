package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.ClientDTO;
import be.pxl.researchproject.domain.*;
import be.pxl.researchproject.exceptions.FoalNotFoundException;
import be.pxl.researchproject.exceptions.InvalidFoalCreatingException;
import be.pxl.researchproject.exceptions.MareNotFoundException;
import be.pxl.researchproject.api.request.CreateFoalRequest;
import be.pxl.researchproject.api.request.DeleteFoalRequest;
import be.pxl.researchproject.api.request.UpdateDewormingRequest;
import be.pxl.researchproject.api.request.UpdateFoalRequest;
import be.pxl.researchproject.api.response.FoalDTO;
import be.pxl.researchproject.repository.CoveringRepository;
import be.pxl.researchproject.repository.DewormingRepository;
import be.pxl.researchproject.repository.FoalRepository;
import be.pxl.researchproject.repository.MareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FoalService {

    @Autowired
    private FoalRepository foalRepository;
    @Autowired
    private MareRepository mareRepository;
    @Autowired
    private DewormingRepository dewormingRepository;
    @Autowired
    private CoveringRepository coveringRepository;

    @Transactional
    public FoalDTO addFoal(Long id, CreateFoalRequest request) {
        Optional<Mare> result = mareRepository.findById(id);

        if (!result.isPresent()) {
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
        else if(result.get().getFoal() != null){
            throw new InvalidFoalCreatingException("Merrie heeft al een veulen");
        } else if (request.name() == null ||
        request.gender() == null ||
        request.height() == 0 ||
        request.dateOfBirth() == null ||
        request.stallion() == null) {
            throw new InvalidFoalCreatingException("Alle velden moeten ingevuld zijn");
        } else {
            String[] medicaties = {"Panacur","Pyrantel","Eraquell","Panacur"};
            Foal foal = new Foal(request.name(), request.dateOfBirth(), request.height(), request.gender(), request.stallion());

            for (int i = 1; i < 5; i++ ){
                String title = "ontworming " + i + " (" + medicaties[i - 1] + ")";
                LocalDate date = foal.getDateOfBirth().plusMonths(i);
                Deworming deworming = new Deworming(title, false, date);
                foal.getDewormings().add(dewormingRepository.save(deworming));
            }

            Mare mare = result.get();

            if (mare.getCoverings().size() == 0){
                throw new InvalidFoalCreatingException("Kan geen veulen toevoegen aan een merrie zonder dekdatums");
            }

            List<Long> coveringIds = mare.getCoverings().stream().map(Covering::getId).toList();

            mare.setDaysPregnant(null);
            mare.setCoverings(null);
            mare.setDaysUntilDueDate(null);
            mare.setDueDate(null);
            mare.setPregnant(false);
            mare.setFoal(foal);

            coveringRepository.deleteAllById(coveringIds);
            Foal newFoal = foalRepository.save(foal);
            mareRepository.save(mare);

            return convertFoalToDTO(newFoal);
        }
    }

    public FoalDTO findFoalByMareId(long id) {
        Optional<Mare> result = mareRepository.findById(id);

        if (result.isPresent()) {
            Mare mare = result.get();
            Foal foal = mare.getFoal();
            if (foal == null) {
                throw new FoalNotFoundException("De merrie heeft geen veulen");
            }
            return new FoalDTO(foal.getId(), foal.getName(), foal.getDateOfBirth(), foal.getHeight(), foal.getGender(), foal.getStallion(), foal.getDewormings());
        } else {
            throw new FoalNotFoundException("Het veulen is niet gevonden");
        }
    }
    public FoalDTO findFoalById(long id) {
        Optional<Foal> result = foalRepository.findById(id);

        if (result.isPresent()) {
            Foal foal = result.get();
            return new FoalDTO(foal.getId(), foal.getName(), foal.getDateOfBirth(), foal.getHeight(), foal.getGender(), foal.getStallion(), foal.getDewormings());
        } else {
            throw new FoalNotFoundException("Het veulen is niet gevonden");
        }
    }

    public void updateDeworming(Long id, UpdateDewormingRequest request){
        Optional<Deworming> result = dewormingRepository.findById(id);
        Deworming deworming = result.orElse(null);
        if (deworming != null && deworming.isDone() != request.isDone()) {
            deworming.setDone(request.isDone());
            dewormingRepository.save(deworming);
        } else if (deworming != null && request.isDone() == deworming.isDone()) {
            throw new InvalidFoalCreatingException("Ontworming niet aangepast");
        } else {
            throw new InvalidFoalCreatingException("Ontworming niet gevonden");
        }
    }

    public void updateFoal(Long id, UpdateFoalRequest request){
        Optional<Foal> result = foalRepository.findById(id);
        Foal foal = result.orElse(null);

        if (foal != null &&
                request.name() != null &&
                request.height() > 0.0 &&
                request.dateOfBirth() != null) {
            foal.setName(request.name());
            foal.setHeight(request.height());
            LocalDate date = LocalDate.parse(request.dateOfBirth());
            foal.setDateOfBirth(date);
            foal.getDewormings().clear();

            String[] medicaties = {"Panacur","Pyrantel","Eraquell","Panacur"};

            for (int i = 1; i < 5; i++ ){
                String title = "ontworming " + i + " (" + medicaties[i - 1] + ")";
                LocalDate newDate = foal.getDateOfBirth().plusMonths(i);
                Deworming deworming = new Deworming(title, false, newDate);
                foal.getDewormings().add(dewormingRepository.save(deworming));
            }

            foalRepository.save(foal);
        } else if (foal != null && (
                request.name() == null ||
                request.height() <= 0.0 ||
                request.dateOfBirth() == null)) {
            throw new FoalNotFoundException("Alle velden moeten ingvuld zijn");
        } else {
            throw new FoalNotFoundException("Het veulen is niet gevonden");
        }
    }

    @Transactional
    public void deleteFoalById(Long id, DeleteFoalRequest request) {
        Optional<Mare> result = mareRepository.findById(request.mareId());
        Mare mare = result.orElse(null);

        if (mare != null && mare.getFoal() != null) {
            mare.setFoal(null);
            mareRepository.save(mare);
            foalRepository.deleteById(id);
        } else if (mare != null && mare.getFoal() == null) {
            throw new FoalNotFoundException("De merrie heeft geen veulen");
        } else {
            throw new MareNotFoundException("De merrie is niet gevonden");
        }
    }

    public boolean amountOfClients(Long id) {
        Optional<Foal> optionalFoal = foalRepository.findById(id);
        if (optionalFoal.isPresent()) {
            Foal foal = optionalFoal.get();
            return foal.getClient() != null;
        } else {
            throw new FoalNotFoundException("Het veulen is niet gevonden");
        }
    }

    private FoalDTO convertFoalToDTO(Foal foal){
        return new FoalDTO(foal.getId(),
                foal.getName(),
                foal.getDateOfBirth(),
                foal.getHeight(),
                foal.getGender(),
                foal.getStallion(),
                foal.getDewormings());
    }
}