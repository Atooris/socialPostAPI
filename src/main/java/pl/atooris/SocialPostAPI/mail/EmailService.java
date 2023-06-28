package pl.atooris.SocialPostAPI.mail;

public interface EmailService {
    void sendVerificationEmail(String email, Long userId);
    void sendSimpleMessage(String to, String subject, String text);
}
