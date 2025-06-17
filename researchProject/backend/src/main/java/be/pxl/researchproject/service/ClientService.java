package be.pxl.researchproject.service;

import be.pxl.researchproject.exceptions.ClientNotFouncException;
import be.pxl.researchproject.exceptions.FoalNotFoundException;
import be.pxl.researchproject.exceptions.InvalidClientCreatingException;
import be.pxl.researchproject.api.request.ClientRequest;
import be.pxl.researchproject.api.request.DeleteClientRequest;
import be.pxl.researchproject.api.response.ClientDTO;
import be.pxl.researchproject.domain.Client;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.repository.ClientRepository;
import be.pxl.researchproject.repository.FoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FoalRepository foalRepository;

    //@Transactional
    public ClientDTO addClient(Long foalId, ClientRequest request){
        Optional<Foal> result = foalRepository.findById(foalId);
        if (result.isEmpty()){
            throw new FoalNotFoundException("Het veulen is niet gevonden.");
        } else if (result.get().getClient() != null) {
            throw new InvalidClientCreatingException("Het veulen heeft al een klant.");
        } else {
            Foal foal = result.get();

            Client client = new Client(
                    request.name(),
                    request.email(),
                    request.phoneNumber(),
                    request.homeAddress(),
                    request.deliveryAddress(),
                    request.movingMonth());

            foal.setClient(client);

            Client newClient = clientRepository.save(client);
            foalRepository.save(foal);

            return convertClientToDTO(newClient);
        }

    }
    public ClientDTO findClientByFoalId(Long id){

        Optional<Foal> result = foalRepository.findById(id);

        if (result.isPresent()){
            Foal foal = result.get();
            Client client = foal.getClient();
            return new ClientDTO(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getHomeAddress(), client.getDeliveryAddress(), client.getMovingMonth(), client.getMovingDate());
        }
        else{
            throw new ClientNotFouncException("Klant niet gevonden");
        }
    }

    @Transactional
    public void updateClient(Long clientId, ClientRequest updateClientRequest){
        Optional<Client> result = clientRepository.findById(clientId);

        if (result.isPresent()){
            Client client = result.get();
            client.setName(updateClientRequest.name());
            client.setEmail(updateClientRequest.email());
            client.setPhoneNumber(updateClientRequest.phoneNumber());
            client.setHomeAddress(updateClientRequest.homeAddress());
            client.setDeliveryAddress(updateClientRequest.deliveryAddress());
            client.setMovingDate(updateClientRequest.movingDate());

            clientRepository.save(client);
        } else {
            throw new ClientNotFouncException("De klant is niet gevonden.");
        }
    }

    @Transactional
    public void deleteClientById(Long clientId, DeleteClientRequest deleteClientRequest){
        Optional<Foal> result = foalRepository.findById(deleteClientRequest.foalId());

        if (result.isPresent()){
            Foal foal = result.get();
            foal.setClient(null);
            foalRepository.save(foal);

            clientRepository.deleteById(clientId);
        } else {
            throw new FoalNotFoundException("Het veulen is niet gevonden.");
        }
    }

    private ClientDTO convertClientToDTO(Client client){
        return new ClientDTO(client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getHomeAddress(),
                client.getDeliveryAddress(),
                client.getMovingMonth(),
                client.getMovingDate());
    }
}
