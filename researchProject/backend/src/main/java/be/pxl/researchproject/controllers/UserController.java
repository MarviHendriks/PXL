package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.ResetPasswordRequest;
import be.pxl.researchproject.api.response.AuthenticationDTO;
import be.pxl.researchproject.api.response.HorseDTO;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.api.request.UserRequest;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.service.AuthenticationService;
import be.pxl.researchproject.service.HorseService;
import be.pxl.researchproject.service.UserService;
import be.pxl.researchproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try{
            AuthenticationDTO user = authenticationService.findUserByEmailAndHashedPassword(userRequest.getEmail(), userRequest.getPassword());

            if (user != null){
                return new ResponseEntity<AuthenticationDTO>(user, HttpStatus.OK);
            }
        }catch (Exception error){
            System.out.println(error);
        }
        return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }
}
