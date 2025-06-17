package be.pxl.researchproject.service;

import be.pxl.researchproject.api.request.ResetPasswordRequest;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.exceptions.FoalNotFoundException;
import be.pxl.researchproject.exceptions.InvalidPasswordException;
import be.pxl.researchproject.exceptions.UserNotFoundException;
import be.pxl.researchproject.repository.UserRepository;
import be.pxl.researchproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final String EMAIL = "test@test.com";
    private static final Roles ROLE = Roles.ADMIN;
    private static final Collection<? extends GrantedAuthority> AUTHORITIES = List.of(new SimpleGrantedAuthority(Roles.ADMIN.name()));
    private static final String HASHEDPASSWORD = "verycoolhash";
    private static final String PASSWORD_TOKEN = "randomToken";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepository;
    @Mock
    private User updatedUser;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @InjectMocks
    private UserServiceImpl userService;

    private final User user = new User(EMAIL, HASHEDPASSWORD, ROLE);

    @Test
    public void getUserDetailsFromEmailShouldReturnValidEmail() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        UserDetails foundUser = userService.getUserDetailsFromEmail(EMAIL);

        assertEquals(EMAIL, foundUser.getUsername());
    }

    @Test
    public void resetUserPasswordTokenShouldThrowErrorWhenUserDoesntExist(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.resetUserPasswordToken(PASSWORD_TOKEN, EMAIL));
        assertEquals("Could not find any user with the email " + EMAIL, exception.getMessage());
    }

    @Test
    public void resetUserPasswordTokenShouldRestPasswordToken(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.resetUserPasswordToken(PASSWORD_TOKEN, EMAIL);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        assertEquals(PASSWORD_TOKEN, savedUser.getResetPasswordToken());
    }

    @Test
    public void getUserByPasswordTokenShouldReturnCorrectUser(){
        user.setResetPasswordToken(PASSWORD_TOKEN);
        when(userRepository.findByResetPasswordToken(PASSWORD_TOKEN)).thenReturn(user);

        UserDTO foundUser = userService.getUserByPasswordToken(PASSWORD_TOKEN);

        assertEquals(EMAIL, foundUser.getEmail());
    }

    @Test
    public void  updatePasswordShouldThrowErrorWhenTheNewPasswordDoesntEqualTheConfirmation(){
        ResetPasswordRequest request = new ResetPasswordRequest(HASHEDPASSWORD, "notTheSame");

        UserDTO userDTO = new UserDTO(user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getAuthorities());

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> userService.updatePassword(userDTO, request));

        assertEquals("Password confirmation does not match", exception.getMessage());
    }

    @Test
    public void  updatePasswordShouldUpdatePassword(){
        String newPassword = HASHEDPASSWORD + "L123?";
        ResetPasswordRequest request = new ResetPasswordRequest(newPassword, newPassword);
        UserDTO userDTO = new UserDTO(user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getAuthorities());

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        userService.updatePassword(userDTO, request);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        assertNull(savedUser.getResetPasswordToken());
        assertTrue(passwordEncoder.matches(newPassword, savedUser.getPassword()));
    }

    @Test
    public void loadUserByUsernameShoudLoadUser(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);
        UserDetails user = userService.loadUserByUsername(EMAIL);
        assertEquals(EMAIL, user.getUsername());
    }

    @Test
    public void getAllUsersShouldReturnAListOfUsers(){
        List<User> users = List.of(new User(EMAIL, HASHEDPASSWORD, ROLE), new User(EMAIL + "2", HASHEDPASSWORD, ROLE));

        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> userDTOS = userService.getAllUsers();

        assertEquals(2, userDTOS.size());
    }
}
