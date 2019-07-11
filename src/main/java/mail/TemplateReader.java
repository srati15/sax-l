package mail;

import java.io.InputStream;
import java.util.Scanner;

public class TemplateReader {
    private final String text;
    public TemplateReader(String fileName){
        InputStream stream=getClass().getClassLoader().getResourceAsStream(fileName);
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        text = s.hasNext() ? s.next() : "";
    }

    public String getText() {
        return text;
    }
}
