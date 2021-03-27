import java.io.*;
import java.util.ArrayList;

public class PackRle {
    private  final String inputName;
    private  final String outputName;

    public PackRle(String inputName, String outputName) {

        this.inputName = inputName;
        this.outputName = outputName;
    }

    /*Кодирование количества повторяющихся символов байтами [-128, -1] для возможности кодирования чисел
      Кодирование количества НЕповторяющихся символов байтами [0, 127] для определения последовательности из неповторяющихся
      символов
     */

     public static void packing(String inputName, String outputName) {
         try (DataInputStream in = new DataInputStream(new FileInputStream(inputName))) {
             try (DataOutputStream out = new DataOutputStream(new FileOutputStream(outputName))) {
                 int repetitiveCount = -1;
                 int notRepetitiveCount = 0;
                 byte previous = 0;
                 boolean oneByte = false;
                 byte current;
                 boolean repetitive = false;
                 boolean afterNotRepetitive = false;
                 boolean afterRepetitive = false;
                 ArrayList<Byte> bytes = new ArrayList<>();
                 if (in.available() == 1) {
                     out.writeByte(in.readByte());
                     oneByte = true;

                 }
                 if (in.available() > 1) {
                     previous = in.readByte();
                 }
                 while (in.available() > 0) {
                     current = in.readByte();
                     if (previous == current) {
                         repetitive = true;
                         repetitiveCount--;
                         if (notRepetitiveCount != 0)  afterNotRepetitive = true;
                     }
                     if (previous != current) {
                         repetitive = false;
                         if (repetitiveCount != -1) afterRepetitive = true;
                     }
                     if (repetitive && repetitiveCount == -128 || afterRepetitive && !repetitive) {
                         out.writeByte(repetitiveCount);
                         out.writeByte(previous);
                         repetitiveCount = -1;
                         if (repetitive) previous = in.readByte();
                         else previous = current;
                         continue;
                     }
                     if (!repetitive) {
                         notRepetitiveCount++;
                         bytes.add(previous);
                     }
                     if (repetitive && afterNotRepetitive || notRepetitiveCount == 127 && !repetitive) {
                         out.writeByte(notRepetitiveCount);
                         for (byte el : bytes) out.writeByte(el);
                         bytes.clear();
                         notRepetitiveCount = 0;
                         previous = current;
                         continue;
                     }
                     previous = current;
                 }
                 if (repetitiveCount != -1) {
                     out.writeByte(repetitiveCount);
                     out.writeByte(previous);
                 }else if (notRepetitiveCount >= 0 && !oneByte) {
                     out.writeByte(notRepetitiveCount + 1);
                     if (!bytes.isEmpty()){
                         for (byte el: bytes) out.writeByte(el);
                         bytes.clear();
                     }
                     out.writeByte(previous);
                 }
             }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public static void unpacking (String inputName, String outputName) {
         try (DataInputStream in = new DataInputStream(new FileInputStream(inputName))){
                 try (DataOutputStream out = new DataOutputStream(new FileOutputStream(outputName))) {
                     if (in.available() == 1) out.writeByte(in.readByte());
                     while (in.available() > 0) {
                         byte length = in.readByte();
                         if (length < 0) {
                             int repetitiveSym = in.readByte();
                             for (int i = 0; i > length; i--) out.write(repetitiveSym);
                         }
                         else {
                             for (int i = 0; i < length; i++) out.write(in.readByte());
                         }
                     }
                 }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }

     }
}
