package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.AuthenticationDTO;
import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    private static final String EMAIL = "test@test.com";
    private static final String HASHEDPASSWORD = "verycoolhash";
    private static final String TOKEN = "generatedToken";
    private static final Roles ROLE = Roles.ADMIN;
    private final User USER = new User(EMAIL, HASHEDPASSWORD, ROLE);
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void findUserByEmailAndHashedPasswordShouldReturnNullWhenUserDoesntExist(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);

        User user = userRepository.findUserByEmail(EMAIL);

        assertNull(user);
    }

    @Test
    public void findUserByEmailAndHashedPasswordShouldReturnAuthenticationDTOWhenValidUserIsGiven(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(USER);
        when(passwordEncoder.matches(any(), anyString())).thenReturn(true);
        when(jwtService.generateToken(any(User.class))).thenReturn(TOKEN);

        AuthenticationDTO authenticationDTO = authenticationService.findUserByEmailAndHashedPassword(EMAIL, HASHEDPASSWORD);

        assertEquals(EMAIL, authenticationDTO.getUserDTO().getEmail());
        assertEquals(TOKEN, authenticationDTO.getToken());
    }
}
