package Security;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class main {
	static String[][] parseCSV(String fileName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String s = br.readLine();
		int fields = 1;
		int index = 0;
		while ((index = s.indexOf(',', index) + 1) > 0)
			fields++;
		int lines = 1;
		while (br.readLine() != null)
			lines++;
		br.close();
		String[][] data = new String[lines][fields];
		Scanner sc = new Scanner(new File(fileName));
		sc.useDelimiter("[,\n]");
		for (int n = 0; n < lines; n++)
			for (int f = 0; f < fields; f++)
				if (sc.hasNext())
					data[n][f] = sc.next();
				else
					System.out.println("Scan error in " + fileName + " at " + n + ":" + f);
		sc.close();
		return data;
	}
	
	
	
	
	public static void main(String[] args) throws Exception {

		String[][] trainingData = parseCSV("E:\\upes\\Semester 5\\Minor Project 1\\Minor\\dummy_dataset.csv");
		String[][] testData = parseCSV("E:\\upes\\Semester 5\\Minor Project 1\\Minor\\dummy_dataset.csv");

		id3 classifier = new id3();
		classifier.train(trainingData);
		classifier.printTree();
		Scanner sc = new Scanner(System.in);
		String nam = " ";
		System.out.println("Enter the name of the attack :");
		nam = sc.nextLine();
		String[] arr = { "Ransomware", "Trojans", "Viruses", "Malware Based", "Phishing", "man in the middle",
				"Password attacks", "SQL Injection", "Dos", "DDoS", "advanced persistent threat",
				"Watering hole attacks", "Cross site scripting", "Cryptojacking", "Url Manipulation",
				"Zero day exploits", "DNS based", "Rootkits", "Session Hijacking", "Spear phishing ",
				"Angler phishing ", "Whaling ", "Brute force", "Dictionary", "Password Spraying", "DNS tunneling",
				"DNS spoofing" };
		int s = 1;
		for (int x = 0; x < arr.length; x++) {
			if (arr[x].equalsIgnoreCase(nam)) {
				s = s + x;
			}
		}
		String algo=" ";
		algo=classifier.classify(testData, nam, s).toString();
		String al=algo.substring(0,algo.length()-1);
		System.out.println(algo.getClass().getName());
		System.out.println("The best security algorithm for the enetered attack is : "+algo);
		System.out.println("Invoking The Security Algorithm:"+algo);
		
		if (al.equalsIgnoreCase("DES") ){
			Security.DES obj=new Security.DES(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("3DES") ){
			Security.tripleDES obj=new Security.tripleDES(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("AES") ){
			Security.AES obj=new Security.AES(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("RSA") ){
			Security.RSA obj=new Security.RSA(); 
			obj.hello();
		}
		if (al.equalsIgnoreCase("ECC") ){
			System.out.println("Invoking ECC Key Agreement");
			Security.ECCKeyAgreement obj=new Security.ECCKeyAgreement();
			obj.main(args);
			System.out.println("Invoking ECC Signature");
			Security.ECCSignature obj1=new Security.ECCSignature();
			obj1.main(args);
		}
		if (al.equalsIgnoreCase("BLOWFISH") ){
			Security.Blowfish obj=new Security.Blowfish(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("TWOFISH") ){
			Security.twoFish obj=new Security.twoFish(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("ELGAMAL") ){
			Security.Elgamal obj=new Security.Elgamal(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("MD5") ){
			Security.md5 obj=new Security.md5();
			obj.main(args);
		}
		if (al.equalsIgnoreCase("SHA1") ){
			Security.sha1 obj=new Security.sha1(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("SHA2") ){
			Security.SHA2 obj=new Security.SHA2(); 
			obj.main(args);
		}
		if (al.equalsIgnoreCase("SHA3") ){
			Security.SHA_3 obj=new Security.SHA_3(); 
			obj.main(args);
		}
		}
		
	}
