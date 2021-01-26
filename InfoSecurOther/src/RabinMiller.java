import java.security.SecureRandom;

public class RabinMiller {
    public static boolean primeCheck(long p) {
        long b = 0;
        long num = p-1;
        SecureRandom random = new SecureRandom();
        while(num % 2 == 0) {
            num/=2;
            b++;
        }
        long m = (p-1)/(long) Math.pow(2,b);
        long a = random.nextInt((int)p);
        long j = 0;
        long z = modPower(a,m,p);
        if (z == 1 || z == p - 1) return true;
        while (true) {
            if (j > 0 && z == 1) return false;
            j++;
            if (j < b && z < p - 1) {
                z = (z * z) % p;
                if (z == p - 1) return true;
                continue;
            }
            if (j == b && z != p - 1) return false;
        }
    }

    public static long modPower(long x, long y, long N)
    {
        if(y==0) return 1;
        long z = modPower(x, y/2, N);
        if ((y % 2) == 0)
            return (z*z)%N;
        else
            return (x*z*z)%N;
    }
}
