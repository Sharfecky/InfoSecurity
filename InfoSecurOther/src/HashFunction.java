import java.util.HashSet;

public class HashFunction {
    public static void main(String[] args) {
        HashSet answer = new HashSet();
        for (int i = 1; i<=100000001; i++) {
            int[] init = toDigitsArray(i);
            int hashInit = hash(init);
            if (!answer.contains(hashInit)) answer.add(hashInit);
            else System.out.println ("WARNING!!! "+hashInit+" Number: " + i);
        }
    }

    public static int hash(int[] num) {
        int hashNum = 0;
        for (int i = 0; i < num.length; i++) {
            hashNum = hashNum + num[i]*(int)Math.pow(11,num.length-i);
        }
        hashNum =hashNum%((int)Math.pow(2,32));
        return hashNum;
    }

    public static int[] toDigitsArray(int num) {
        return toDigitsArray(num, 1);
    }

    private static int[] toDigitsArray(int num, int n) {
        int[] digits;

        if (num >= 10) {
            digits = toDigitsArray(num / 10, n + 1);
        } else {
            digits = new int[n];
        }
        digits[digits.length - n] = num % 10;
        return digits;
    }
}
