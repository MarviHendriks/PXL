package be.pxl.researchproject.domain;

import be.pxl.researchproject.api.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    private static final String EMAIL = "test@test.com";
    private static final Roles ROLE = Roles.ADMIN;
    private static final Collection<? extends GrantedAuthority> AUTHORITIES = List.of(new SimpleGrantedAuthority(Roles.ADMIN.name()));
    private static final String HASHEDPASSWORD = "verycoolhash";
    private static final String PASSWORD_TOKEN = "randomToken";

    @Test
    public void userConstructorShouldSetEveryPropertyCorrectly(){
        User user = new User(EMAIL, HASHEDPASSWORD, ROLE);

        assertEquals(EMAIL, user.getUsername());
        assertEquals(ROLE, user.getRole());
        assertEquals(HASHEDPASSWORD, user.getPassword());
        assertEquals(AUTHORITIES, user.getAuthorities());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertEquals("User{" +
                "email='" + EMAIL + '\'' +
                ", role=" + ROLE.name() +
                '}', user.toString());
    }

    @Test
    public void userGettersAndSettersShouldWorkCorrectly(){
        User user = new User(EMAIL, HASHEDPASSWORD, ROLE);

        user.setResetPasswordToken(PASSWORD_TOKEN);

        assertEquals(PASSWORD_TOKEN, user.getResetPasswordToken());
        assertEquals(HASHEDPASSWORD, user.getPassword());
    }
}
