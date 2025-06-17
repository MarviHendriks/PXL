package be.pxl.researchproject.api.request;

import be.pxl.researchproject.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserRequestTest {
    private static String EMAIL = "test@test.com";
    private static String PASSWORD = "password";

    @Test
    public void userRequestConstructorShouldSetEveryPropertyCorrectly(){
        UserRequest request = new UserRequest(EMAIL, PASSWORD);
        assertEquals(EMAIL, request.getEmail());
        assertEquals(PASSWORD, request.getPassword());
    }

    @Test
    public void userRequestGetterAndSettersShouldWorkCorrectly(){
        UserRequest request = new UserRequest();

        request.setEmail(EMAIL);
        request.setPassword(PASSWORD);

        assertEquals(EMAIL, request.getEmail());
        assertEquals(PASSWORD, request.getPassword());
    }
}
