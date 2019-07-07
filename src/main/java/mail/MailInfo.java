package mail;

import java.io.IOException;
import java.util.Properties;

public class MailInfo {

    private final Properties properties = new Properties();
    public MailInfo(){
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
