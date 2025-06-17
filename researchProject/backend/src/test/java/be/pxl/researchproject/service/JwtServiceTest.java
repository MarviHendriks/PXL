package be.pxl.researchproject.service;

import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    private String token;

    @BeforeEach
    void setUp() {
        token = jwtService.generateToken(new User("test@test.com", "password", Roles.ADMIN));
    }
    @Test
    void isValid_WithValidTokenAndMatchingUser_ReturnsTrue() {
        when(userDetails.getUsername()).thenReturn("test@test.com");
        assertTrue(jwtService.isValid(token, userDetails));
    }

    @Test
    void getUserRole_WithValidToken_ReturnsUserRole() {
        assertEquals(Roles.ADMIN, jwtService.getUserRole(token));
    }

    @Test
    void getUserEmail_WithValidToken_ReturnsUserEmail() {
        assertEquals("test@test.com", jwtService.getUserEmail(token));
    }

    @Test
    void extractClaim_WithValidTokenAndClaim_ReturnsClaimValue() {
        String role = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals(Roles.ADMIN, Roles.valueOf(role));
    }
}