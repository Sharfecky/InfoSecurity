import java.math.BigInteger;
import java.security.SecureRandom;

public class SRPServer {
    byte[] s;
    String I, K, R, M;
    BigInteger v, A, N, g, u, B, S, b;
    BigInteger k = new BigInteger("3");
    SecureRandom rand = new SecureRandom();
    SRPServer(String username, byte[] salt, BigInteger passver, BigInteger N, BigInteger g){
        this.I = username;
        this.s = salt;
        this.v = passver;
        this.N = N;
        this.g = g;
    }
    public BigInteger serverAuth(BigInteger A) {
        if (A.equals(BigInteger.ZERO)) System.out.println("Server: A=0, connection refused");
        this.A = A;
        this.b = BigInteger.valueOf(rand.nextLong());
        this.B = ((g.modPow(b,N)).add(v.multiply(k))).mod(N);
        return B;
    }
    public void serverSetU() {
        this.u = new BigInteger(SHA512.getSHA(B.toString(), A.toString()),16);
        if (u.equals(BigInteger.ZERO)) System.out.println("Server: u=0, connection suspended");
    }
    public void serverSetSK() {
        this.S = (A.multiply(v.modPow(u,N))).modPow(b,N);
        this.K = SHA512.getSHA(S.toString());
    }
    public String serverConfirm(String clientM) {
        BigInteger hashN = new BigInteger(SHA512.getSHA(N.toString()),16);
        BigInteger hashG = new BigInteger(SHA512.getSHA(g.toString()),16);
        String hashI = SHA512.getSHA(I);
        this.M = SHA512.getSHA((hashN.xor(hashG)).toString(),hashI,S.toString(),A.toString(),B.toString(),K);
        if (!M.equals(clientM)) {
            System.out.println("Server: clientM and serverM not equals, connection suspended");
            return null;
        }
        else {
            R = SHA512.getSHA(A.toString(), M, K);
            return R;
        }
    }
}
