package be.pxl.researchproject.config;
import be.pxl.researchproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final JwtAuthentication jwtAuthentication;

    public SecurityConfig(UserService userService, JwtAuthentication jwtAuthentication) {
        this.userService = userService;
        this.jwtAuthentication = jwtAuthentication;
    }

    //.hasAuthority("ADMIN") in plaats van prevent all om te zeggen dat alleen admin toegang krijgt
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //login moet niet met een token geautorisseerd worden, elk ander pagina wel
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/login/**",
                                "/mares",
                                "/mares/search",
                                "/mares/search/{id}",
                                "/mares/{id}/diary",
                                "/mares/{id}/foals",
                                "/mares/{id}/coverDates",
                                "/mares/filter",
                                "/mares/{id}/coverings",
                                "/stallions",
                                "/stallions/search",
                                "/stallions/search/{id}",
                                "/stallions/filter",
                                "/horses",
                                "/horses/search",
                                "/foals/search/{id}",
                                "/foals/search/foal/{id}",
                                "/foals/{id}/clients",
                                "/clients/search/{id}",
                                "/password/**",
                                "/password/send/**",
                                "/resetPassword/**",
                                "/clients/search/{id},",
                                "/notifications",
                                "/mares/{id}/pdf/read",
                                "/stallions/{id}/pdf/read").permitAll()
                        .requestMatchers("/mares/**",
                                "/foals/**",
                                "/stallions/**",
                                "/clients/**").hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(userService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtAuthentication, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}