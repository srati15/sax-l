package mail;

import dao.helpers.FinalBlockExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class MailInfo {
    private static final Logger logger = LogManager.getLogger(MailInfo.class);

    private final Properties properties = new Properties();
    public MailInfo(){
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
