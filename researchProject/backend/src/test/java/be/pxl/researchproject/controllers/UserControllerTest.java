package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.UserRequest;
import be.pxl.researchproject.api.response.AuthenticationDTO;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.service.AuthenticationService;
import be.pxl.researchproject.service.UserService;
import be.pxl.researchproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private static final String EMAIL = "test@test.com";
    private static final String HASHEDPASSWORD = "verycoolhash";
    private static final String TOKEN = "generatedToken";
    private static final Roles ROLE = Roles.ADMIN;
    private static final Collection<? extends GrantedAuthority> AUTHORITIES = List.of(new SimpleGrantedAuthority(Roles.ADMIN.name()));

    private static final UserDTO userDTO = new UserDTO(1L, EMAIL, ROLE, AUTHORITIES);
    private final List<UserDTO> userDTOS = List.of(new UserDTO(1L, EMAIL, ROLE, AUTHORITIES), new UserDTO(2L, EMAIL + "2", ROLE, AUTHORITIES));

    @Mock
    private UserServiceImpl userService;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private UserController userController;

    @Test
    public void getAllUsersShouldReturnAListOfUsers(){
        when(userService.getAllUsers()).thenReturn(userDTOS);

        List<UserDTO> response = userController.getAllUsers();

        assertEquals(userDTOS.size(), response.size());
    }

    @Test
    public void loginShouldThrowErrorWhenUserDoesntExist(){
        when(authenticationService.findUserByEmailAndHashedPassword(anyString(), anyString())).thenReturn(null);

        UserRequest userRequest = new UserRequest(EMAIL, HASHEDPASSWORD);

        ResponseEntity<?> response = userController.login(userRequest);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Incorrect username or password", response.getBody());
    }

    @Test
    public void loginShouldReturnUserLoginInfo(){
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(TOKEN, userDTO);
        when(authenticationService.findUserByEmailAndHashedPassword(anyString(), anyString())).thenReturn(authenticationDTO);

        UserRequest userRequest = new UserRequest(EMAIL, HASHEDPASSWORD);

        ResponseEntity<?> response = userController.login(userRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(authenticationDTO, response.getBody());
    }

    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try{
            AuthenticationDTO user = authenticationService.findUserByEmailAndHashedPassword(userRequest.getEmail(), userRequest.getPassword());

            if (user != null){
                return new ResponseEntity<AuthenticationDTO>(user, HttpStatus.OK);
            }
        }catch (Exception error){
            System.out.println(error);
        }
        return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    }
}
