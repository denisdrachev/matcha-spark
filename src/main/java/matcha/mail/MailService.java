package matcha.mail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.Application;
import matcha.exception.db.SendRegistrationMailException;
import matcha.properties.ConfigProperties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;

@Slf4j
//@Component
//@AllArgsConstructor
//@NoArgsConstructor
public class MailService {

//    private JavaMailSender mailSender;
//    private ConfigProperties configProperties;

    //TODO return void
    public boolean sendRegistrationMail(String userEmail, String userActivationCode) {
//        try {
//            if (!configProperties.isMailSend())
//                return true;
//            String subject = "Registration Confirmation";
//            String confirmationUrl = "/regitrationConfirm.html?token=".concat(userActivationCode);
//            String message = "hello manz";
//
//            SimpleMailMessage email = new SimpleMailMessage();
//            email.setTo(userEmail);
//            email.setSubject(subject);
////        email.setText(message.concat("\r\n<a href=https://matcha-server.herokuapp.com").concat(confirmationUrl).concat(" target=\"_blank\">Активация</a>"));
//            email.setText(message.concat("\r\n<a href=http://localhost:8888").concat(confirmationUrl).concat(" target=\"_blank\">Активация</a>"));
//
//            log.info("Sending mail: ".concat(email.toString()));
//            mailSender.send(email);
//            log.info("Message sent.");
//            return true;
//        } catch (Exception e) {
//            log.warn("Exception. sendRegistrationMail: {}", e.getMessage());
//            throw new SendRegistrationMailException();
//        }

        return true;
    }

    public boolean sendResetPasswordEmail(String userEmail, String userActivationCode) {
//        try {
//            if (!configProperties.isMailSend())
//                return true;
//            String subject = "Reset password";
//            String confirmationUrl = "/password-reset.html?token=".concat(userActivationCode);
//            String« message = "hello manz";
//
//            SimpleMailMessage email = new SimpleMailMessage();
//            email.setTo(userEmail);
//            email.setSubject(subject);
////        email.setText(message.concat("\r\n<a href=https://matcha-server.herokuapp.com").concat(confirmationUrl).concat(" target=\"_blank\">Активация</a>"));
//            email.setText(message.concat("\r\n<a href=http://localhost:" + Application.getHerokuAssignedPort()).concat(confirmationUrl).concat(" target=\"_blank\">Сбросить </a>"));
//
//            log.info("Sending mail: ".concat(email.toString()));
//            mailSender.send(email);
//            log.info("Message sent.");
//            return true;
//        } catch (Exception e) {
//            log.warn("Exception. sendRegistrationMail: {}", e.getMessage());
//            throw new SendRegistrationMailException();
//        }
        return true;
    }

    public boolean sendMessage(String email, String subject, String text) {

        final String username = "matcha0aamxqo91@gmail.com";
        final String password = "wdih287182g2uheiuhHYATSA";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("matcha0aamxqo91@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            log.info("Send message to '{}', with subject '{}' done.", email, subject);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;
    }
}
