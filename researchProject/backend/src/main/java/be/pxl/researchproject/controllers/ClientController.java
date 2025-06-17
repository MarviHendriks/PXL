package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.ClientRequest;
import be.pxl.researchproject.api.request.DeleteClientRequest;
import be.pxl.researchproject.api.response.ClientDTO;
import be.pxl.researchproject.domain.Client;
import be.pxl.researchproject.service.ClientService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients/search/{id}")
    public ClientDTO getClientByFoalId(@PathVariable Long id){
        return clientService.findClientByFoalId(id);
    }


    @PostMapping("/clients/foal/{id}/add")
    public ResponseEntity<ClientDTO> createClientForFoal(@PathVariable Long id, @RequestBody ClientRequest createClientRequest){
        ClientDTO client = clientService.addClient(id, createClientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }


    @PutMapping("/clients/{id}/update")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody ClientRequest updateClientRequest){
        clientService.updateClient(id, updateClientRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clients/{id}/delete")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id, @RequestBody DeleteClientRequest deleteClientRequest){
        clientService.deleteClientById(id, deleteClientRequest);
        return ResponseEntity.noContent().build();
    }

}
