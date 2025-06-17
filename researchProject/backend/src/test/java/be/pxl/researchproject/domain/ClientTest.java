package be.pxl.researchproject.domain;

import be.pxl.researchproject.api.request.ClientRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClientTest {
    private static final String NAME = "Kevin";
    private static final String EMAIL = "test@test.com";
    private static final String PHONENUMBER = "0499999999";
    private static final String HOMEADDRESS = "adres 1";
    private static final String DELIVERYADDRESS = "adres 2";
    private static final String MOVINGMONTH = "January";

    @Test
    public void clientConstructorShouldSetEveryPropertyCorrectly(){
        Client client = new Client(NAME, EMAIL, PHONENUMBER, HOMEADDRESS, DELIVERYADDRESS, MOVINGMONTH);

        assertEquals(NAME, client.getName());
        assertEquals(EMAIL, client.getEmail());
        assertEquals(PHONENUMBER, client.getPhoneNumber());
        assertEquals(HOMEADDRESS, client.getHomeAddress());
        assertEquals(DELIVERYADDRESS, client.getDeliveryAddress());
        assertEquals(MOVINGMONTH, client.getMovingDate());
    }

    @Test
    public void GettersAndSettersShouldWorkCorrectly(){
        Client client = new Client();

        client.setName(NAME);
        client.setEmail(EMAIL);
        client.setPhoneNumber(PHONENUMBER);
        client.setHomeAddress(HOMEADDRESS);
        client.setDeliveryAddress(DELIVERYADDRESS);
        client.setMovingMonth(MOVINGMONTH);

        assertEquals(NAME, client.getName());
        assertEquals(EMAIL, client.getEmail());
        assertEquals(PHONENUMBER, client.getPhoneNumber());
        assertEquals(HOMEADDRESS, client.getHomeAddress());
        assertEquals(DELIVERYADDRESS, client.getDeliveryAddress());
        assertEquals(MOVINGMONTH, client.getMovingMonth());
    }
}
