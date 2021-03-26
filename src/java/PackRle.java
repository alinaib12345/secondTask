import java.io.*;
import java.util.ArrayList;

public class PackRle {
    private  final String inputName;
    private  final String outputName;

    public PackRle(String inputName, String outputName) {

        this.inputName = inputName;
        this.outputName = outputName;
    }

    /*Кодирование количества повторяющихся символов байтами [-128, -1] для возможности кодирования чисел из одинаковых цифр
      Кодирование количества НЕповторяющихся символов байтами [0, 127] для определения последовательности из неповторяющихся
      символов
     */

     public static void packing(String inputName, String outputName) {
         try (DataInputStream in = new DataInputStream(new FileInputStream(inputName))) {
             try (DataOutputStream out = new DataOutputStream(new FileOutputStream(outputName))) {
                 int repetitiveCount = -1;
                 int notRepetitiveCount = 0;
                 byte previous = 0;
                 if (in.available() != 0) {
                     previous = in.readByte();
                 }
                 byte current;
                 ArrayList<Byte> bytes = new ArrayList<>();
                 while (in.available() > 0) {
                     current = in.readByte();
                     if (previous == current) {
                         if (notRepetitiveCount != 0) {
                             out.writeByte(notRepetitiveCount);
                             for (byte el : bytes) out.writeByte(el);
                             bytes.clear();
                             notRepetitiveCount = 0;
                         }
                         repetitiveCount--;
                         if (repetitiveCount == -128) {
                             out.writeByte(repetitiveCount);
                             out.writeByte(previous);
                             repetitiveCount = -1;
                             previous = in.readByte();
                             continue;
                         }
                     }
                     if (previous != current) {
                         if (repetitiveCount != -1) {
                             out.writeByte(repetitiveCount);
                             out.writeByte(previous);
                             repetitiveCount = -1;
                             previous = current;
                             continue;
                         } else {
                             notRepetitiveCount++;
                             bytes.add(previous);
                             if (notRepetitiveCount == 127) {
                                 out.writeByte(notRepetitiveCount);
                                 for (byte el : bytes) out.writeByte(el);
                                 bytes.clear();
                                 notRepetitiveCount = 0;
                                 previous = current;
                                 continue;
                             }

                         }

                     }
                     previous = current;
                 }
                 if (notRepetitiveCount != 0) {
                     out.writeByte(notRepetitiveCount + 1);
                     for (byte el: bytes) out.writeByte(el);
                     out.writeByte(previous);
                 } else if (repetitiveCount < -1) {
                     out.writeByte(repetitiveCount);
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