package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.FoalDTO;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {
    private static final String FROM_MAIL = "tinkerstalboesehof@gmail.com";

    @Mock
    private UserServiceImpl userService;
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailService mailService;

    @Test
    public void generateTokenShouldGenerateAValidToken(){
        doNothing().when(userService).resetUserPasswordToken(anyString(), anyString());
        String token = mailService.generateToken(FROM_MAIL);

        assertEquals(30, token.length());
    }

    @Test
    public void sendMailShouldSendMail(){
        mailService.sendMail(FROM_MAIL);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
