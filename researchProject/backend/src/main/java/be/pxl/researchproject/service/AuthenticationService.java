package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.AuthenticationDTO;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationDTO findUserByEmailAndHashedPassword(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = userRepository.findUserByEmail(email);

        if(user != null && passwordEncoder.matches(password, user.getPassword()) ){
            String token = jwtService.generateToken(user);
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getRole(), user.getAuthorities());
            return new AuthenticationDTO(token,userDTO);
        }
        else{
            return null;
        }
    }
}
