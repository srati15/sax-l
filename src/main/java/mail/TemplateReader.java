package mail;

import java.io.InputStream;
import java.util.Scanner;

public class TemplateReader {
    private String text;
    public TemplateReader(){
        InputStream stream=getClass().getClassLoader().getResourceAsStream("recovery.html");
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        text = s.hasNext() ? s.next() : "";
    }

    public String getText() {
        return text;
    }
}
