package matcha.mail;

import lombok.extern.slf4j.Slf4j;
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
        try {
//            String url = "<a href=\"http://localhost:4567/registration-check?token=\" " + userActivationCode + " target=\"_blank\">ссылка</a>";
            String url = ConfigProperties.baseUrl + /*":" + ConfigProperties.basePort +*/ "/verification?token=" + userActivationCode;
            String subject = "Регистрация Matcha";
            String text = "Воу воу, ковбой, ты зарегался, поздравляю! Осталось активировать учетную запись по ссылке:\n" + url;
            if (ConfigProperties.emailSend) {
                sendMessage(userEmail, subject, text);
            }
            log.info("Registration message send to '{}' need message send? {}", userEmail, ConfigProperties.emailSend);
            return true;
        } catch (Exception e) {
            log.warn("Exception. sendRegistrationMail: {}", e.getMessage());
            throw new SendRegistrationMailException();
        }
    }

    public boolean sendResetPasswordEmail(String userEmail, String userActivationCode) {
        try {
//            String url = "<a href=\"http://localhost:4567/registration-check?token=\" " + userActivationCode + " target=\"_blank\">ссылка</a>";
            String url = ConfigProperties.baseUrl + /*":" + ConfigProperties.basePort +*/ "/verification?token=" + userActivationCode;
            String subject = "Смена пароля Matcha";
            String text = "Воу воу, ковбой, ты хочешь сменить пароль - ноу проблем. Переходи по ссылке и делай свои делишки:\n" + url;
            if (ConfigProperties.emailSend) {
                sendMessage(userEmail, subject, text);
            }
            log.info("Reset password email send to '{}' need message send? {}", userEmail, ConfigProperties.emailSend);
            return true;
        } catch (Exception e) {
            log.warn("Exception. sendResetPasswordEmail: {}", e.getMessage());
            throw new SendRegistrationMailException();
        }
    }

    private boolean sendMessage(String email, String subject, String text) {

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
