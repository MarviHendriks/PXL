package be.pxl.researchproject.service.impl;

import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.exceptions.InvalidPasswordException;
import be.pxl.researchproject.exceptions.UserNotFoundException;
import be.pxl.researchproject.api.request.ResetPasswordRequest;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.repository.UserRepository;
import be.pxl.researchproject.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails getUserDetailsFromEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void resetUserPasswordToken(String token, String email) {
        var user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with the email " + email);
        }
    }

    @Override
    public UserDTO getUserByPasswordToken(String token) {
        User user = userRepository.findByResetPasswordToken(token);
        return new UserDTO(user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getAuthorities());
    }

    @Override
    public void updatePassword(UserDTO userDTO, ResetPasswordRequest passwordRequest) {
        if (passwordRequest.passwordConfirmation().equals(passwordRequest.newPassword())){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(passwordRequest.newPassword());
            User user = userRepository.findUserByEmail(userDTO.getEmail());
            user.setPassword(encodedPassword);
            user.setResetPasswordToken(null);
            userRepository.save(user);
        } else {
            throw new InvalidPasswordException("Password confirmation does not match");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            return new UserDTO(user.getId(), user.getUsername(), user.getRole(), user.getAuthorities());
        }).toList();
    }
}
