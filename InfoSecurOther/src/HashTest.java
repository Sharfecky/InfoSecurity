import java.util.HashSet;

public class HashTest {
    public static void main(String[] args) {
        int b;
        HashSet set = new HashSet();
        for (int a = 1;a<10000;a++) {
            int divide = 4;
            int[] aArr = toDigitsArray(a); //Поразрядно пихаем цифры в массив
            String aBinary = "";
            String decNumStr = "";
            for (int i = 0; i < aArr.length; i++) aBinary = aBinary + Integer.toBinaryString(aArr[i]); //Преобразуем цифры в двоичную сс
            while (aBinary.length() % divide != 0) aBinary = aBinary.concat("0"); //Дополняем нулями справа, чтобы число цифр было кратно 4
            while (aBinary.length() > 0) {
                String nextChunk = aBinary.substring(0, divide); //Делим строку на равные части, по 4 разряда
                decNumStr = decNumStr.concat(Integer.toString(Integer.valueOf(nextChunk, 2))); // Запихиваем переведенные в десятичную сс 4 разряда в строку
                aBinary = aBinary.substring(divide); //Идем делить строку по 4 дальше
            }
            int decNum = Integer.parseInt(decNumStr); //Получили DecNum
            b = lcm(a, decNum); //НОК входного числа и DecNum
            aArr = toDigitsArray(b); //Разбиваем этот НОК на массив снова поразрядно
            for (int i = 0; i < aArr.length; i++) {
                divide = aArr[i];
                aArr[i] = aArr[divide % aArr.length];
                aArr[divide % aArr.length] = divide; //Меняем местами элементы массива согласно исходному алгоритму
            }
            b = toInt(aArr); //Преобразуем массив цифр в число
            if (!set.contains(b)) set.add(b); //Если полученного хэша не было, добавляем
            System.out.println ("WARNING!!! \nValue:\n"+a+"Hash:"+b); //иначе ошибка
        }
    }
    public static int[] toDigitsArray(int num) { //Рекурсивный алгоритм преобразования числа в поразрядный массив
        return toDigitsArray(num, 1);
    }

    private static int[] toDigitsArray(int num, int n) {
        int[] digits;

        if (num > 10) {
            digits = toDigitsArray(num / 10, n + 1);
        } else {
            digits = new int[n];
        }
        digits[digits.length - n] = num % 10;
        return digits;
    }
    static int lcm(int a, int b) //Поиск НОК
    {
        return (a / gcd(a, b)) * b;
    }
    static int gcd(int a, int b) //Поиск НОД
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }
    static int toInt(int[] array) { //Преобразуем массив цифр в число
        int result = 0;
        int offset = 1;
        for(int i = array.length - 1; i >= 0; i--) {
            result += array[i]*offset;
            offset *= 10;
        }
        return result;
    }
}
