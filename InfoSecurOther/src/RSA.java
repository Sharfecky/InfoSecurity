import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.valueOf(PrimeNumGen.primeGen());
        BigInteger q = BigInteger.valueOf(PrimeNumGen.primeGen());
        BigInteger n = p.multiply(q);
        BigInteger p1 = p.subtract(BigInteger.valueOf(1));
        BigInteger q1 = q.subtract(BigInteger.valueOf(1));
        BigInteger phin = p1.multiply(q1);
        long e = 0;
        gcd(e, phin.longValue());
        while ((e%2==0) || (gcd(e, phin.longValue()) != 1)) {
            e = Math.abs(random.nextInt(1000));
        }
        BigInteger e2 = BigInteger.valueOf(e);
        BigInteger d = e2.modInverse(phin);
        BigInteger m = BigInteger.valueOf(123456789);
        System.out.println("Message: " + m);
        BigInteger x = m.modPow(e2, n);
        System.out.println("RSA(Message): " + x);
        System.out.println("Decryption(Message): "+ x.modPow(d, n));
    }
    public static long gcd(long a,long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}
