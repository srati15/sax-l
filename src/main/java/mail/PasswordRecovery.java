package mail;

import datatypes.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class PasswordRecovery {
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

            System.out.println("Password Recovery mail sent");
            return true;
        } catch (MessagingException e) {
            System.out.println("Invalid mail");
        }
        return false;
    }

}
