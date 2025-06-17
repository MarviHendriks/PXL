package be.pxl.researchproject.api.response;

import be.pxl.researchproject.api.request.UserRequest;
import be.pxl.researchproject.domain.Roles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationDTOTest {
    private static String TOKEN = "token";
    private static UserDTO USERDTO = new UserDTO(1L, "test@test.com", Roles.ADMIN, List.of(new SimpleGrantedAuthority(Roles.ADMIN.name())));

    @Test
    public void authenticationDTOConstructorShouldSetEveryPropertyCorrectly(){
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(TOKEN, USERDTO);

        assertEquals(TOKEN, authenticationDTO.getToken());
        assertEquals(USERDTO, authenticationDTO.getUserDTO());
    }
}
