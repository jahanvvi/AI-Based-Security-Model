package Security;
import javax.crypto.*;
import java.util.*;
public class tripleDES
{
private static Cipher cipher1 = null;
public static void main(String[] args) throws Exception
{
KeyGenerator kg = KeyGenerator.getInstance("DESede");
kg.init(112);
SecretKey sk = kg.generateKey();
cipher1 = Cipher.getInstance("DESede");
Scanner s= new Scanner(System.in);
System.out.println("Enter Text to encrypt: ");
String p=s.nextLine();
byte[] pByte = p.getBytes("UTF8");
byte[] eByte = encrypt(pByte, sk);
String c = new String(eByte, "UTF8");
System.out.println("Encrypted Text is: " +c);
byte[] dByte = decrypt(eByte, sk);
String plain = new String(dByte, "UTF8");
System.out.println("Decrypted Text is: " +plain);
}
static byte[] encrypt(byte[] pByte, SecretKey sk) throws Exception
{
cipher1.init(Cipher.ENCRYPT_MODE, sk);
byte[] eByte = cipher1.doFinal(pByte);
return eByte;
}
static byte[] decrypt(byte[] eByte, SecretKey sk) throws Exception
{
cipher1.init(Cipher.DECRYPT_MODE, sk);
byte[] dByte = cipher1.doFinal(eByte);
return dByte;
}
}