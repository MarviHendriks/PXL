package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.ResetPasswordRequest;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.service.MailService;
import be.pxl.researchproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ForgotPasswordControllerTest {
    private static final String EMAIL = "test@test.com";
    private static final String HASHEDPASSWORD = "verycoolhash";
    private static final String PASSWORD_TOKEN = "randomToken";
    private static final Roles ROLE = Roles.ADMIN;
    private static final Collection<? extends GrantedAuthority> AUTHORITIES = List.of(new SimpleGrantedAuthority(Roles.ADMIN.name()));
    private final UserDTO USER = new UserDTO(1L, EMAIL, ROLE, AUTHORITIES);
    private final ResetPasswordRequest REQUEST = new ResetPasswordRequest(HASHEDPASSWORD, HASHEDPASSWORD);

    @Mock
    private MailService mailService;
    @Mock
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    @Test
    public void sendMailShouldSendMail(){
        doNothing().when(mailService).sendMail(anyString());

        String response = forgotPasswordController.sendMail(EMAIL);

        assertEquals("succes", response);
    }

    @Test
    public void resetPasswordShouldReturnBadRequestWhenUserDoesntExist(){
        when(userServiceImpl.getUserByPasswordToken(anyString())).thenReturn(null);

        ResponseEntity<UserDTO> response = forgotPasswordController.resetPassword(PASSWORD_TOKEN, REQUEST);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void resetPasswordShouldResetPassword(){
        when(userServiceImpl.getUserByPasswordToken(anyString())).thenReturn(USER);
        doNothing().when(userServiceImpl).updatePassword(any(UserDTO.class), any(ResetPasswordRequest.class));

        ResponseEntity<UserDTO> response = forgotPasswordController.resetPassword(PASSWORD_TOKEN, REQUEST);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(USER, response.getBody());
    }
}
