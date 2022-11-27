package Security;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AES {
    private SecretKey key;
    private int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private Cipher encryptionCipher;
    public  void in(){
        Scanner sc = new Scanner(System.in);
        int flag =0;
        while(flag == 0){
            System.out.println("Enter the size of the key : 128 , 192, 256");
            int keySize =sc.nextInt();
            if(keySize == 128 || keySize == 192 || keySize == 256)
            {
                KEY_SIZE=keySize;
                flag =1;
            }
        }

    }

    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args) {
        try {
            AES aes = new AES();
            aes.init();
            aes.in();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter text to be encrpyted: ");
            String input = sc.nextLine();
            String encryptedMessage = aes.encrypt(input);
            String decryptedMessage = aes.decrypt(encryptedMessage);

            System.err.println("Encrypted Message : " + encryptedMessage);
            System.err.println("Decrypted Message : " + decryptedMessage);
        } catch (Exception ignored) {
        }
    }
}