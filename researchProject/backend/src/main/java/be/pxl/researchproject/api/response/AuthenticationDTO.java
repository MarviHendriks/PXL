package be.pxl.researchproject.api.response;

public class AuthenticationDTO {
    private String token;
    private UserDTO userDTO;

    public AuthenticationDTO(String token, UserDTO userDTO) {
        this.token = token;
        this.userDTO = userDTO;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
