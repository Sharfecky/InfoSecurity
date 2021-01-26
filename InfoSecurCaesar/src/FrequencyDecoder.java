import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDecoder {
    public static boolean ASC = true;
    public static boolean DESC = false;

    public static void main(String[] args) {
        Map<Character, Integer> mapOrig = new HashMap<>();
        Map<Character, Integer> mapGlava = new HashMap<>();
        Pattern pattern = Pattern.compile("[а-яА-Я]");
        try {
            File newFile = new File("glava(decoded).txt");
            newFile.createNewFile();
            FileWriter writer = new FileWriter("glava(decoded).txt");
            FileReader readerOrig = new FileReader("voyna&mir.txt");
            FileReader readerGlava1 = new FileReader("glava(coded).txt");
            int c = 0;
            while ((c = readerOrig.read()) != -1) {
                Matcher matcher = pattern.matcher(Character.toString((char) c));
                if (matcher.find()) {
                    c = Character.toLowerCase((char) c);
                    if (mapOrig.containsKey((char) c)) mapOrig.put((char) c, mapOrig.get((char) c) + 1);
                    else mapOrig.put((char) c, 1);
                }
            }
            while ((c = readerGlava1.read()) != -1) {
                Matcher matcher = pattern.matcher(Character.toString((char) c));
                if (matcher.find()) {
                    c = Character.toLowerCase((char) c);
                    if (mapGlava.containsKey((char) c)) mapGlava.put((char) c, mapGlava.get((char) c) + 1);
                    else mapGlava.put((char) c, 1);
                }
            }
            readerGlava1.close();
            mapOrig = sortByComparator(mapOrig, DESC);
            mapGlava = sortByComparator(mapGlava, DESC);
            Map<Character, Character> mapDecode = new HashMap<>();
            char[] arrayOrig = new char[mapOrig.size()];
            char [] arrayGlava = new char[mapGlava.size()];
            int i = 0, j = 0;
            for (Character a:mapOrig.keySet()) arrayOrig[i++] = a;
            for (Character a:mapGlava.keySet()) arrayGlava[j++] = a;
            for (i = 0; i < arrayOrig.length; i++) mapDecode.put(arrayGlava[i],arrayOrig[i]);
            FileReader readerGlava2 = new FileReader("glava(coded).txt");
            while ((c = readerGlava2.read()) != -1) {
                Matcher matcher = pattern.matcher(Character.toString((char) c));
                if (matcher.find()) {
                    c = Character.toLowerCase((char) c);
                    writer.write(mapDecode.get((char)c));
                }
                else writer.write((char)c);
            }
            writer.close();
            readerGlava2.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Map<Character, Integer> sortByComparator(Map<Character, Integer> unsortMap, final boolean order) {

        List<Entry<Character, Integer>> list = new LinkedList<Entry<Character, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Character, Integer>>() {
            public int compare(Entry<Character, Integer> o1,
                               Entry<Character, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Entry<Character, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}

