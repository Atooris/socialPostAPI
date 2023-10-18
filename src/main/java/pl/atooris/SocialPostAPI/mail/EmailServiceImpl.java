package pl.atooris.SocialPostAPI.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.atooris.SocialPostAPI.exception.EmailException;
import pl.atooris.SocialPostAPI.security.SecurityConstants;
import pl.atooris.SocialPostAPI.security.manager.JWTTokenManager;

@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    JavaMailSender emailSender;
    JWTTokenManager jwtTokenManager;

    public void sendVerificationEmail(String email, Long userId){
        String token = jwtTokenManager.createVerificationToken(email);
        String link = SecurityConstants.DOMAIN_PATH + "user/" + userId + "/confirm/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xatooris@gmail.com");
        message.setTo(email);
        message.setSubject("Verification");
        message.setText("Hi, to finish your account verification process please in link below.\n" + link);

        try{
            emailSender.send(message);
        } catch (Exception e){
            throw new EmailException();
        }

    }

    public void sendSimpleMessage(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xatooris@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
