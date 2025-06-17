package be.pxl.researchproject.api.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClientRequestTest {
    private static final String NAME = "Kevin";
    private static final String EMAIL = "test@test.com";
    private static final String PHONENUMBER = "0499999999";
    private static final String HOMEADDRESS = "adres 1";
    private static final String DELIVERYADDRESS = "adres 2";
    private static final String MOVINGMONTH = "januari";
    private static final LocalDate MOVINGDATE = LocalDate.now();

    @Test
    public void createFoalRequestConstructorShouldSetEveryPropertyCorrectly(){
        ClientRequest request = new ClientRequest(NAME, EMAIL, PHONENUMBER, HOMEADDRESS, DELIVERYADDRESS, MOVINGMONTH, MOVINGDATE);

        assertEquals(NAME, request.name());
        assertEquals(EMAIL, request.email());
        assertEquals(PHONENUMBER, request.phoneNumber());
        assertEquals(HOMEADDRESS, request.homeAddress());
        assertEquals(DELIVERYADDRESS, request.deliveryAddress());
        assertEquals(MOVINGMONTH, request.movingMonth());
        assertEquals(MOVINGDATE, request.movingDate());
    }
}
