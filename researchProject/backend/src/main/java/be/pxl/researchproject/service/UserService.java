package be.pxl.researchproject.service;

import be.pxl.researchproject.api.request.ResetPasswordRequest;
import be.pxl.researchproject.api.response.UserDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetails getUserDetailsFromEmail(String email);
    void resetUserPasswordToken(String token, String email);
    UserDTO getUserByPasswordToken(String token);
    void updatePassword(UserDTO userDTO, ResetPasswordRequest resetPasswordRequest);
}