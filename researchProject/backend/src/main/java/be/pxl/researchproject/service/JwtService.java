package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "bc6adb28e3b059d0f4d0dc99586f85bc854a7bea5310f0ba41468fd63e00a247dbd218db63fecdab5255eb90a62d70e3fd4c869299f6079d0d75029e387b7ce6";

    public boolean isValid(String token, UserDetails user){
        return !isTokenExpired(token) && getUserEmail(token).equals(user.getUsername());
    }

    private boolean isTokenExpired(String token) {
        return extractExperiation(token).before(new Date());
    }

    private Date extractExperiation(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //even in comment voor testing
    /*public String getUserRole(String token){
        return extractClaim(token, Claims::getSubject);
    }*/
    public Roles getUserRole(String token) {
        String roleString = extractClaim(token, Claims::getSubject);
        return Roles.valueOf(roleString);
    }

    public String getUserEmail(String token){
        Claims claims = extractAllClaims(token);
        return (String) claims.get("email");
    }

    //haal een claim uit van een lijst van claims
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    //ontvang en lees alle properties in de token dus, user rol, datum van aanmaak, vervaldatum,...
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Genereer een jwt token die voor 7 dagen gelding blijft
    public String generateToken(User user){
        String token = Jwts
                .builder()
                .subject(user.getRole().toString())
                .claim("email", user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)))
                .signWith(getSignInKey())
                .compact();
        return token;
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
