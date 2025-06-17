package be.pxl.researchproject.controllers;

import be.pxl.researchproject.api.request.ResetPasswordRequest;
import be.pxl.researchproject.api.response.UserDTO;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.User;
import be.pxl.researchproject.service.MailService;
import be.pxl.researchproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgotPasswordController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/password/send/{mail}")
    public String sendMail(@PathVariable String mail){
        mailService.sendMail(mail);
        return "succes";
    }

    @PostMapping("/password/reset/{token}")
    public ResponseEntity<UserDTO> resetPassword(@PathVariable String token, @RequestBody ResetPasswordRequest resetPasswordRequest){
        UserDTO user = userServiceImpl.getUserByPasswordToken(token);
       if(user != null){
           userServiceImpl.updatePassword(user, resetPasswordRequest);
           return ResponseEntity.status(HttpStatus.CREATED).body(user);
       }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }
}
