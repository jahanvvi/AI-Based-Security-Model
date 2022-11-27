package Security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class md5{
	public static String encryptThisString(String input)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);

			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String args[]) throws
									NoSuchAlgorithmException
	{

		Scanner sc=new Scanner(System.in);
        System.out.println("Enter the message:->");
        String message=sc.nextLine();
        System.out.println(encryptThisString(message));
	}
}

        