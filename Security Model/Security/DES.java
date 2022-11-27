package Security;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
public class DES {

	    // Creating an instance of the Cipher class for ENCRYPTION
	    private static Cipher encrypt;
	    // Creating an instance of the Cipher class for DECRYPTION
	    private static Cipher decrypt;
	    // Initializing Vector
	    private static final byte[] initialization_vector = { 22, 33, 11, 44, 55, 99, 66, 77 };
	    // main() method
	    public static void main(String[] args)
	    {
	        Scanner sc = new Scanner(System.in);
	        // Create a file and enter it's path that you want to encrypt
	        System.out.println("ENTER THE PATH OF THE FILE THAT YOU WANT TO ENCRYPT: ");
	        String textFile = sc.nextLine();
	        // Create a file and enter it's path which is the encrypted file that we get as output
	        System.out.println("ENTER THE PATH OF THE ENCRYPTED FILE: ");
	        String encryptedData = sc.nextLine();
	        // Create a file and enter it's path which is the  decrypted file that we get as output
	        System.out.println("ENTER THE PATH OF THE DECRYPTED FILE: ");
	        String decryptedData = sc.nextLine();
	        try
	        {
	            // Generating keys by using the KeyGenerator class
	            SecretKey secretkey = KeyGenerator.getInstance("DES").generateKey();
	            AlgorithmParameterSpec aps = new IvParameterSpec(initialization_vector);
	            // Setting encryption mode
	            encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
	            encrypt.init(Cipher.ENCRYPT_MODE, secretkey, aps);
	            // Setting decryption mode
	            decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
	            decrypt.init(Cipher.DECRYPT_MODE, secretkey, aps);
	            // Calling encrypt() method to encrypt the file
	            encryption(new FileInputStream(textFile), new FileOutputStream(encryptedData));
	            // Calling decrypt() method to decrypt the file
	            decryption(new FileInputStream(encryptedData), new FileOutputStream(decryptedData));
	            // Prints the statement if the program runs successfully
	            System.out.println("The encrypted and decrypted files have been created successfully.");
	        }
	        // Catching multiple exceptions by using the | (or) operator in a single catch block
	        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IOException e)
	        {
	            // Prints the message (if any) related to exceptions
	            e.printStackTrace();
	        }
	    }
	    // Method for encryption
	    private static void encryption(InputStream input, OutputStream output)
	            throws IOException
	    {
	        output = new CipherOutputStream(output, encrypt);
	        // Calling the writeBytes() method to write the encrypted bytes to the file
	        writeBytes(input, output);
	    }
	    // Method for decryption
	    private static void decryption(InputStream input, OutputStream output)
	            throws IOException
	    {
	        input = new CipherInputStream(input, decrypt);
	        // Calling the writeBytes() method to write the decrypted bytes to the file
	        writeBytes(input, output);
	    }
	    // Method for writting bytes to the files
	    private static void writeBytes(InputStream input, OutputStream output) throws IOException
	    {
	        byte[] writeBuffer = new byte[512];
	        int readBytes = 0;
	        while ((readBytes = input.read(writeBuffer)) >= 0)
	        {
	            output.write(writeBuffer, 0, readBytes);
	        }
	        // Closing the output stream
	        output.close();
	        // Closing the input stream
	        input.close();
	    }
	}

