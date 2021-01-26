import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Coder {
    public static void main(String[] args) {

        try {
            File newFile = new File("glava(coded).txt");
            newFile.createNewFile();
            FileWriter writer = new FileWriter("glava(coded).txt");
            FileReader reader = new FileReader("glava(src).txt");
            int c;
            while ((c=reader.read())!=-1) {
                if ((c >= 1040 && c< 1070) || (c > 1071 && c < 1102)) writer.write((char)(c+2));
                else switch (c) {
                    case ((char)1070): writer.write('А'); break; //Э
                    case ((char)1071): writer.write('Б'); break; //э
                    case ((char)1102): writer.write('а'); break; //Я
                    case ((char)1103): writer.write('б'); break; //я
                    case ((char)1025): writer.write('З'); break; //Ё
                    case ((char)1105): writer.write('з'); break; //ё
                    default: writer.write((char)c); break;
                }
            }
            writer.close();
            reader.close();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
