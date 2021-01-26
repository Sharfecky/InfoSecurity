import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Vector;

public class DiffieHellman {
    public static void main (String args[]) {
        if(alice()) System.out.println("Success!");
    }
    public static boolean alice() {
        try {
            File aOut = new File("AOut.txt");
            File bOut = new File("BOut.txt");
            if (aOut.createNewFile()) System.out.println("File created: " + aOut.getName());
            if (bOut.createNewFile()) System.out.println("File created: " + bOut.getName());
            FileWriter writer = new FileWriter(aOut);
            SecureRandom randNum = new SecureRandom();
            long p = PrimeNumGen.primeGen();
            long g = modRoot(p);
            if (g == -1) System.out.println("g <- ERROR!");
            int xa = Math.abs(randNum.nextInt());
            while (xa >= p) xa = Math.abs(randNum.nextInt());
            long ya = modPower(g,xa,p);
            writer.write(g+","+p+","+ya);
            writer.close();
            bob();
            String content = Files.readString(Path.of("BOut.txt"));
            String[] bOutStr = content.split(",");
            long yb = Integer.parseInt(bOutStr[0]);
            long kb = Integer.parseInt(bOutStr[1]);
            long ka = modPower(yb,xa,p);
            if (ka == kb) return true;
            else return false;
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void bob() {
        try {
            SecureRandom randNum = new SecureRandom();
            int xb = Math.abs(randNum.nextInt());
            String content = Files.readString(Path.of("AOut.txt"));
            FileWriter writer = new FileWriter("BOut.txt");
            String[] aOutStr = content.split(",");
            long g = Integer.parseInt(aOutStr[0]);
            long p = Integer.parseInt(aOutStr[1]);
            long ya = Integer.parseInt(aOutStr[2]);
            while (xb >= p) xb = Math.abs(randNum.nextInt());
            long yb = modPower(g,xb,p);
            long kb = modPower(ya,xb,p);
            writer.write(yb+","+kb);
            writer.close();
        } catch (IOException e) {

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

    public static long modRoot(long p) {
        Vector numVec = new Vector();
        boolean isModRoot = true;
        for (int i = 1; i < p; i++) {
            for (int j = 1; j <= p - 1; j++) {
                numVec.add(modPower(i, j, p));
            }
            for (int j = 1; j <= p - 1; j++) {
                if (!numVec.contains(((long) j))) {
                    isModRoot = false;
                    break;
                }
            }
            if (isModRoot) return i;
            else {
                isModRoot = true;
                numVec.clear();
            }
        }
        return -1;
    }
}

