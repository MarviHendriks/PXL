package be.pxl.researchproject.api.response;

import be.pxl.researchproject.domain.Foal;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClientDTOTest {
    private static final Long ID = 1L;
    private static final String NAME = "Kevin";
    private static final String EMAIL = "test@test.com";
    private static final String PHONENUMBER = "0499999999";
    private static final String HOMEADDRESS = "adres 1";
    private static final String DELIVERYADDRESS = "adres 2";
    private static final String MOVINGMONTH = "Januari";
    private static final LocalDate MOVINGDATE = LocalDate.now();

    @Test
    public void clientDTOConstructorShouldSetEveryPropertyCorrectly(){
        ClientDTO clientDTO = new ClientDTO(ID, NAME, EMAIL, PHONENUMBER, HOMEADDRESS, DELIVERYADDRESS, MOVINGMONTH, MOVINGDATE);

        assertEquals(ID, clientDTO.getId());
        assertEquals(NAME, clientDTO.getName());
        assertEquals(EMAIL, clientDTO.getEmail());
        assertEquals(PHONENUMBER, clientDTO.getPhoneNumber());
        assertEquals(HOMEADDRESS, clientDTO.getHomeAddress());
        assertEquals(DELIVERYADDRESS, clientDTO.getDeliveryAddress());
        assertEquals(MOVINGMONTH, clientDTO.getMovingMonth());
        assertEquals(MOVINGDATE, clientDTO.getMovingDate());
    }

    @Test
    public void clientDTOGettersAndSettersShouldWorkCorrectly(){
        ClientDTO clientDTO = new ClientDTO(0, null, null, null, null, null, null, null);

        clientDTO.setId(ID);
        clientDTO.setName(NAME);
        clientDTO.setEmail(EMAIL);
        clientDTO.setPhoneNumber(PHONENUMBER);
        clientDTO.setHomeAddress(HOMEADDRESS);
        clientDTO.setDeliveryAddress(DELIVERYADDRESS);
        clientDTO.setMovingMonth(MOVINGMONTH);

        assertEquals(ID, clientDTO.getId());
        assertEquals(NAME, clientDTO.getName());
        assertEquals(EMAIL, clientDTO.getEmail());
        assertEquals(PHONENUMBER, clientDTO.getPhoneNumber());
        assertEquals(HOMEADDRESS, clientDTO.getHomeAddress());
        assertEquals(DELIVERYADDRESS, clientDTO.getDeliveryAddress());
        assertEquals(MOVINGMONTH, clientDTO.getMovingMonth());
    }
}
