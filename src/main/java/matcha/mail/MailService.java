package matcha.mail;

import lombok.extern.slf4j.Slf4j;
import matcha.exception.db.SendRegistrationMailException;
import matcha.properties.ConfigProperties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class MailService {


    public boolean sendRegistrationMail(String userEmail, String userActivationCode) {
        try {
            String url = "<a href=\"" + ConfigProperties.baseUrl + "/verification?token=" + userActivationCode + "\" target=\"_blank\">клац</a>";
            String subject = "Регистрация Matcha";
            String text = "Воу воу, ковбой, ты зарегался, поздравляю!</br>Осталось активировать учетную запись по ссылке:</br>" + url;
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
            String url = "<a href=\"" + ConfigProperties.baseUrl + "/change-password?token=" + userActivationCode + "\" target=\"_blank\">клац</a>";
            String subject = "Смена пароля Matcha";
            String text = "Воу воу, ковбой, ты хочешь сменить пароль - ноу проблем.</br>Переходи по ссылке и делай свои делишки:</br>" + url;
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
            message.setContent(text, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress("matcha0aamxqo91@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject(subject);
            Transport.send(message);
            log.info("Send message to '{}', with subject '{}' done.", email, subject);
        } catch (MessagingException e) {
            log.warn("Error send message to {}", email);
        }
        return true;
    }
}
