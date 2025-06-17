package be.pxl.researchproject.config;

import be.pxl.researchproject.domain.Roles;
import be.pxl.researchproject.service.JwtService;
import be.pxl.researchproject.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.Console;
import java.io.IOException;

@Component
public class JwtAuthentication extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthentication(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    //Telkens als een request naar de backend gestuurt word, checkt deze functie eerst voor de authenticatie dus als hij/zij admin is of niet
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //haalt de header uit de request
        final String jwt;
        final Roles userRole;
        final String userEmail;

        if (authHeader == null || !(authHeader.startsWith("Bearer "))){
            filterChain.doFilter(request, response);//geeft de request en response door aan de volgende filter
            return;
        }

        jwt = authHeader.substring(7);//haal de token uit de header
        userRole = jwtService.getUserRole(jwt);
        userEmail = jwtService.getUserEmail(jwt);

        //check of de user al niet eerder geauthenticeerd is of niet
        if (userRole != null && userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.getUserDetailsFromEmail(userEmail);

            if (jwtService.isValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
