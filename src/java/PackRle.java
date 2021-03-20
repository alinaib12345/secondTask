import org.apache.commons.lang3.math.NumberUtils;
import java.io.*;

public class PackRle {

    private final boolean pack;
    private  final String inputName;
    private  final String outputName;

    public PackRle(boolean pack, String inputName, String outputName) {
        this.pack = pack;
        this.inputName = inputName;
        this.outputName = outputName;
    }

    public static String packing(String line) {
        if (line.equals("")) return "";
        StringBuilder result = new StringBuilder();
        int count = 1;
        char previous = line.charAt(0);
        char current;
        for (int i = 1; i < line.length(); i++) {
            current = line.charAt(i);
            if (previous == current) count++;
            else {
                if(count == 1) result.append(previous);
                else {
                    result.append(count).append(previous);
                    count = 1;
                }
            }
            previous = current;
        }
        if (count > 1) result.append(count);
        result.append(previous);
        return result.toString();
    }

    public static String unpacking(String line) {
        StringBuilder result = new StringBuilder();
        StringBuilder number = new StringBuilder();
        char sym;
        for (int i = 0; i < line.length(); i++) {
            sym = line.charAt(i);
            if (Character.isDigit(sym)) number.append(sym);
            else {
                if (number.toString().isEmpty()) result.append(sym);
                else for (int j = 0; j < NumberUtils.toInt(number.toString()); j++)
                    result.append(sym);
                number = new StringBuilder();
            }
        }
        return result.toString();
    }

    public void packRle() {
        try {
            File inputFile = new File(inputName);
            File fileToWrite = new File("fileToWrite.txt");
            FileReader reader = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(reader);
            FileWriter fw;
            if (!outputName.equals("")) fw = new FileWriter(outputName);
            else  fw = new FileWriter(fileToWrite);
            BufferedWriter bw = new BufferedWriter(fw);
            String newString;
            for (String line; (line = br.readLine()) != null;) {
                if (pack) {
                    newString = packing(line);
                } else newString = unpacking(line);
                bw.write(newString);
                bw.newLine();
            }
            if (outputName.equals("")) {
                inputFile.delete();
                fileToWrite.renameTo(inputFile);
            }
            if (pack) System.out.println("Pack-rle: pack successful");
            else System.out.println("Pack-rle: unpack successful");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}




