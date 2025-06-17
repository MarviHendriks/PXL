package be.pxl.researchproject.api.response;

import be.pxl.researchproject.domain.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserDTO {
    private Long id;
    private String email;
    private Roles role;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDTO(Long id,
                   String email,
                   Roles role,
                   Collection<? extends GrantedAuthority> authorities
                   ) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Roles getRole() {
        return role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
