import java.math.BigInteger;
import java.security.SecureRandom;

public class SRPClient {
    public static void main(String[] args) {
        SecureRandom rand = new SecureRandom();
        long q = 0, N1 = 0;
        boolean prime = false;
        while(!prime) {
            q = PrimeNumGen.primeGen();
            N1 = 2*q+1;
            prime = true;
            for (int i = 0; i<6; i++) if (!RabinMiller.primeCheck(N1)) prime = false;
        }
        BigInteger N = BigInteger.valueOf(N1);
        BigInteger g = BigInteger.valueOf(DiffieHellman.modRoot(N1));
        BigInteger k = new BigInteger("3");
        byte[] s = new byte[5];
        rand.nextBytes(s);
        String I = "roxal";
        String p = "33211233";
        BigInteger x = new BigInteger(SHA512.getSHA(new String(s),p),16);
        BigInteger v = g.modPow(x,N);
        SRPServer srpServ = new SRPServer(I, s, v, N, g); //Отправляем на сервер логин, соль, верификатор пароля, N и g
        BigInteger a = BigInteger.valueOf(rand.nextLong());
        BigInteger A = g.modPow(a,N);
        BigInteger B = srpServ.serverAuth(A); //Отправляем А и получаем B
        if (B.equals(BigInteger.ZERO)) System.out.println("Client: B = 0, connection refused");
        BigInteger u = new BigInteger(SHA512.getSHA(B.toString(), A.toString()),16); //Вычисляем u
        if (u.equals(BigInteger.ZERO)) System.out.println("Client: u = 0, connection suspended");
        srpServ.serverSetU(); //Вычисляем u на сервере
        BigInteger S = (B.subtract(k.multiply(v))).modPow(a.add(u.multiply(x)),N);
        String K = SHA512.getSHA(S.toString());
        srpServ.serverSetSK(); //Вычисляем S и K на сервере
        BigInteger hashN = new BigInteger(SHA512.getSHA(N.toString()),16);
        BigInteger hashG = new BigInteger(SHA512.getSHA(g.toString()),16);
        String hashI = SHA512.getSHA(I);
        String M = SHA512.getSHA((hashN.xor(hashG)).toString(),hashI,S.toString(),A.toString(),B.toString(),K);
        String servR = srpServ.serverConfirm(M); //Сравниваем M на клиенте и сервере, при успехе получаем R
        if (servR.equals(null)) System.out.println("Client: Server suspended connection due to M not equals");
        else {
            String R = SHA512.getSHA(A.toString(), M, K);
            if (R.equals(servR)) System.out.println("Connection successful!");
            else System.out.println("Client: R not equals, connection suspended");
        }
    }
}
