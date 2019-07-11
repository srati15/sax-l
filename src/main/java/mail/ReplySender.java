package mail;

import datatypes.messages.AdminMessage;
import datatypes.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ReplySender {
    private static final Logger logger = LogManager.getLogger(ReplySender.class);

    private static final Properties properties = new MailInfo().getProperties();
    private static final String htmlTemplate = new TemplateReader("reply.html").getText();
    public static boolean send(AdminMessage adminMessage, String reply) {

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty("mail.smtp.username"), properties.getProperty("mail.smtp.password"));
                    }
                });
        try {
            if (isValidMail(adminMessage.getMail())) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.from")));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adminMessage.getMail()));
                message.setSubject("Re : "+adminMessage.getSubject());
                String copy = htmlTemplate;
                copy = copy.replaceAll("%FIRSTNAME%", adminMessage.getName());
                copy = copy.replaceAll("%REPLY_TEXT%", reply);

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(copy, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                message.setContent(multipart);

                Transport.send(message);

                logger.info("Reply mail sent");
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
