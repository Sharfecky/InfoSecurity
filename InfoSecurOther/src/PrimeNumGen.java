import java.security.SecureRandom;
import java.util.*;

public class PrimeNumGen {
    public static void main(String args[]) {
        System.out.println(primeGen());
    }
    public static int primeGen() {
        SecureRandom random = new SecureRandom();
        List<Integer> lowPrimeNum = primeNumbersBruteForce(2000);
        Boolean prime = false;
        int randNum = 0;
        while(!prime) {
            prime = true;
            randNum = random.nextInt(65536);
            randNum = randNum | 0x8001;
            for (int i = 0; i < lowPrimeNum.size(); i++) {
                if (randNum%lowPrimeNum.get(i) == 0) {
                    prime = false;
                    break;
                }
            }
            if (!prime) continue;
            else {
                for (int i = 0; i<5; i++) {
                    if (!RabinMiller.primeCheck(randNum)) {
                        prime = false;
                        break;
                    }
                }
            }
        }
        return randNum;
    }

    public static List<Integer> primeNumbersBruteForce(int n) {
        List<Integer> primeNumbers = new LinkedList<>();
        if (n >= 2) {
            primeNumbers.add(2);
        }
        for (int i = 3; i <= n; i += 2) {
            if (isPrimeBruteForce(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
    private static boolean isPrimeBruteForce(int number) {
        for (int i = 2; i*i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
