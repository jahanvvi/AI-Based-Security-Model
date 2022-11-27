package Security;

import java.math.BigInteger;
import java.util.Scanner;
class RSA {

    static int gcd(int a, int h) {
        int temp;
        while (true) {
            temp = a % h;
            if (temp == 0)
                return h;
            a = h;
            h = temp;
        }
    }
    static int findD(int phi, int e) {

        int i;
        for (i = 1; i < phi; i++) {
            if ((i * e) % phi == 1) {
                break;
            }
        }
        return i;
    }
    public void hello () {
        Scanner sc = new Scanner(System.in);
        int p, q;
        System.out.print("Enter Value of P = ");
        p = sc.nextInt();
        System.out.print("Enter Value of Q = ");
        q = sc.nextInt();
        int n;
        n = p * q;
        System.out.println("Value of N = (P * Q) => " + n);
        int Phi_n;
        Phi_n = (p - 1) * (q - 1);
        System.out.println("Value of Phi of N = (P-1) * (Q-1) => " + Phi_n);
        int e = 2;
        while (e < Phi_n) {
            if (gcd(e, Phi_n) == 1)
                break;
            else
                e++;
        }
        System.out.println("value of E  = " + e);
        int d;
        d = findD(Phi_n, e);
        System.out.println("Value of D = " + d);
        System.out.println("\tYour Public key = { " + e + " , " + n + " } ");
        System.out.println("\tYour Private key = { " + d + " , " + n + " } ");
        System.out.print("Enter a Character = ");
        char P = sc.next().charAt(0);
        int K = P;
        BigInteger BI_K = new BigInteger(String.valueOf(K));
        BigInteger BI_N = new BigInteger(String.valueOf(n));
        BigInteger CT = (BI_K.pow(e)).mod(BI_N);
        BigInteger PT = (CT.pow(d)).mod(BI_N);
        int Decrypted_Int = PT.intValue();
        char Decrypted_Char = (char) Decrypted_Int;
        System.out.println("Plain Text = " + P);
        System.out.println("Cypher Text = " + CT);
    }
}