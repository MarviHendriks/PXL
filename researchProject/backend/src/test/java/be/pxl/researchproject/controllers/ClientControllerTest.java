package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.ClientRequest;
import be.pxl.researchproject.api.request.DeleteClientRequest;
import be.pxl.researchproject.api.response.ClientDTO;
import be.pxl.researchproject.domain.Client;
import be.pxl.researchproject.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    void getClientByFoalId_FoalId_ReturnsClientDTO() {
        ClientDTO expectedClientDTO = new ClientDTO(1L, "John Doe", "john.doe@example.com", "+123456789", "123 Main Street", "456 Elm Street", "January", null);
        when(clientService.findClientByFoalId(1L)).thenReturn(expectedClientDTO);

        ClientDTO result = clientController.getClientByFoalId(1L);

        assertEquals(expectedClientDTO, result);
    }

    @Test
    void createClientForFoal_ValidRequest_ReturnsCreatedResponseWithClient() {
        ClientRequest request = new ClientRequest("John Doe", "john.doe@example.com", "+123456789", "123 Main Street", "456 Elm Street", "January", LocalDate.now());
        ClientDTO expectedClient = new ClientDTO(1L ,"John Doe", "john.doe@example.com", "+123456789", "123 Main Street", "456 Elm Street", "January", LocalDate.now());

        when(clientService.addClient(1L, request)).thenReturn(expectedClient);
        ResponseEntity<ClientDTO> response = clientController.createClientForFoal(1L, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedClient, response.getBody());
    }

    @Test
    void updateClient_ValidRequest_ReturnsNoContentResponse() {
        ClientRequest request = new ClientRequest("John Doe", "john.doe@example.com", "+123456789", "123 Main Street", "456 Elm Street", "January", LocalDate.now());

        ResponseEntity<Void> response = clientController.updateClient(1L, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService, times(1)).updateClient(1L, request);
    }

    @Test
    void deleteClient_ValidRequest_ReturnsNoContentResponse() {
        DeleteClientRequest request = new DeleteClientRequest(1L);
        ResponseEntity<Void> response = clientController.deleteClient(1L, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService, times(1)).deleteClientById(1L, request);
    }
}