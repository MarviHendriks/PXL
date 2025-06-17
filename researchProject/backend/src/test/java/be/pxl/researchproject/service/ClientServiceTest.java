package be.pxl.researchproject.service;

import be.pxl.researchproject.exceptions.ClientNotFouncException;
import be.pxl.researchproject.api.response.ClientDTO;
import be.pxl.researchproject.domain.Client;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.repository.ClientRepository;
import be.pxl.researchproject.repository.FoalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private static final String CLIENT_NAME = "Kevin";
    private static final String MOVING_MONTH = "Januari";
    private static final String EMAIL = "Kevin.Hendricks@gmail.com";
    private static final String PHONENUMBER = "+32478652349";
    private static final String HOME_ADDRESS = "Elfdeliniestraat 10 Hasselt";
    private static final String DELIVERY_ADDRESS = "Elfdeliniestraat 10 Hasselt";

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private FoalRepository foalRepository;
    @Mock
    private Client newClient;

    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    @InjectMocks
    private ClientService clientService;
    private final Client client = new Client(CLIENT_NAME, EMAIL, PHONENUMBER, HOME_ADDRESS, DELIVERY_ADDRESS, MOVING_MONTH);
    private final Foal foal = new Foal("Becky", LocalDate.now(), 1.7, "Foal", "Mari");

    @Test
    public void throwsExceptionWhenClientWithInvalidFoalIdIsGiven(){
        when(foalRepository.findById(anyLong())).thenReturn(Optional.empty());
        ClientNotFouncException exception = assertThrows(ClientNotFouncException.class, () -> clientService.findClientByFoalId(1L));
        assertEquals("Klant niet gevonden", exception.getMessage());
    }

    @Test
    public void foalWithValidIdShouldReturnClient(){
        client.setId(2L);
        foal.setClient(client);
        when(foalRepository.findById(1L)).thenReturn(Optional.of(foal));
        ClientDTO foundClient = clientService.findClientByFoalId(1L);
        assertEquals(client.getId(), foundClient.getId());
        assertEquals(client.getName(), foundClient.getName());
        assertEquals(client.getMovingMonth(), foundClient.getMovingMonth());
        assertEquals(client.getEmail(), foundClient.getEmail());
        assertEquals(client.getPhoneNumber(), foundClient.getPhoneNumber());
        assertEquals(client.getHomeAddress(), foundClient.getHomeAddress());
        assertEquals(client.getDeliveryAddress(), foundClient.getDeliveryAddress());
    }

}
