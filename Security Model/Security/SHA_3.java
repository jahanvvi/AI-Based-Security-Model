package Security;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA_3 {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";
    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String algorithm = "SHA3-256";
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the message:->");
        String pText =sc.nextLine();
        System.out.println(String.format(OUTPUT_FORMAT, "Input (string)", pText));
        System.out.println(String.format(OUTPUT_FORMAT, "Input (length)", pText.length()));
        byte[] shaInBytes = SHA_3.digest(pText.getBytes(UTF_8), algorithm);
        System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(shaInBytes)));
        System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (length)", shaInBytes.length));
    }
}


