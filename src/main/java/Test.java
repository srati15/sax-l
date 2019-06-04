import com.mysql.cj.xdevapi.JsonArray;
import datatypes.FormField;
import datatypes.SelectField;
import enums.InputType;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] ags) {
        JSONObject userName = new JSONObject(new FormField("username",  InputType.text, true, 4));
        JSONObject password = new JSONObject(new FormField("password",  InputType.password, true, 4));
        JSONObject select = new JSONObject(new SelectField("usertype", Arrays.asList("admin", "user")));
        System.out.println(select);
        JSONArray array1 = new JSONArray();
        array1.put(userName);
        array1.put(password);

        JSONArray selects = (JSONArray) select.get("selectStrings");
        for (int i =0; i < selects.length(); i++) {
            System.out.println(selects.get(i));
        }
    }
}
