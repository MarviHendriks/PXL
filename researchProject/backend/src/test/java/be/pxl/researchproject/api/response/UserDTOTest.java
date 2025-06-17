package be.pxl.researchproject.api.response;

import be.pxl.researchproject.api.request.UserRequest;
import be.pxl.researchproject.domain.Roles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserDTOTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "test@test.com";
    private static final Roles ROLE = Roles.ADMIN;
    private static final Collection<? extends GrantedAuthority> AUTHORITIES = List.of(new SimpleGrantedAuthority(Roles.ADMIN.name()));


    @Test
    public void userDTOConstructorShouldSetEveryPropertyCorrectly(){
        UserDTO userDTO = new UserDTO(ID, EMAIL, ROLE, AUTHORITIES);

        assertEquals(ID, userDTO.getId());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(ROLE, userDTO.getRole());
        assertEquals(AUTHORITIES, userDTO.getAuthorities());
    }
}
