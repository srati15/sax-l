package mail;

import datatypes.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class PasswordRecovery {
    private static final Logger logger = LogManager.getLogger(PasswordRecovery.class);

    private static final Properties properties = new MailInfo().getProperties();
    private static final String htmlTemplate = new TemplateReader().getText();
    public static boolean send(User user, String password) {

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty("mail.smtp.username"), properties.getProperty("mail.smtp.password"));
                    }
                });
        try {
            if (isValidMail(user.getMail())) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.from")));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getMail()));
                message.setSubject("Password Recovery | Sax-l");
                String copy = htmlTemplate;
                copy = copy.replaceAll("%USERNAME%", user.getUserName());
                copy = copy.replaceAll("%FIRSTNAME%", user.getFirstName());
                copy = copy.replaceAll("%LASTNAME%", user.getLastName());
                copy = copy.replaceAll("%TEMP_PASSWORD%", password);

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(copy, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                message.setContent(multipart);

                Transport.send(message);

                logger.info("Password Recovery mail sent");
                return true;
            }
        } catch (MessagingException e) {
            logger.error(e);
        }
        return false;
    }
    private static boolean isValidMail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            logger.error(ex);
            return false;
        }
        return true;
    }
}
