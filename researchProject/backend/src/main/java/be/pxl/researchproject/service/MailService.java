package be.pxl.researchproject.service;

import be.pxl.researchproject.service.impl.UserServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    private UserServiceImpl userServiceImpl;

    public void sendMail(String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        String token = generateToken(mail);
        message.setFrom(fromMail);
        message.setTo(mail);
        message.setSubject("Reset password");
        message.setText("Klik op onderstaande link om een nieuw wachtwoord in te vullen\n" + "http://localhost:5173/password/reset/token/" + token + "\n\nAls de bovenstaande link niet werkt gelieve de onderstaande te gebruiken.\n" + "http://localhost:5174/password/reset/token/" + token);
        mailSender.send(message);
    }

    public String generateToken(String email){
        String token = RandomString.make(30);
        userServiceImpl.resetUserPasswordToken(token, email);
        return token;
    }
}
