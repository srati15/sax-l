package security;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cracker {

    // Array of chars used to produce strings
    public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();


    private static String hexToString(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        for (int aByte : bytes) {
            int val = aByte;
            val = val & 0xff;  // remove higher bits, sign
            if (val < 16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public static String code(String word){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] bytes = word.getBytes(StandardCharsets.UTF_8);
            return (hexToString(md.digest(bytes)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;

    }

}

