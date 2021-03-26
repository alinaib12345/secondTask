import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class Tests {

    @Test
    public void packRle() {
        PackLauncher.main(("-z -out test/resources/output.txt test/resources/input.txt").split(" "));
        PackLauncher.main(("-u test/resources/output.txt").split(" "));
        PackLauncher.main(("-z test/resources/input1.txt").split(" "));
        PackLauncher.main(("-u test/resources/input1.txt.rle").split(" "));
        PackLauncher.main(("-z test/resources/input2.txt").split(" "));
    }
    @Test
    public void packRle1() throws IOException {
        PackRle.packing("test/resources/input1.txt","test/resources/rletest.txt");
        PackRle.unpacking("test/resources/rletest.txt","test/resources/output1.txt");
        File expected = new File("test/resources/input1.txt");
        File actual = new File("test/resources/output1.txt");
        assertTrue(FileUtils.contentEquals(expected, actual));
    }

    @Test
    public void packRle2() throws IOException {
        PackRle.packing("test/resources/input2.txt","test/resources/rletest2.txt");
        PackRle.unpacking("test/resources/rletest2.txt","test/resources/output2.txt");
        File expected = new File("test/resources/input2.txt");
        File actual = new File("test/resources/output2.txt");
        assertTrue(FileUtils.contentEquals(expected, actual));
    }

    @Test
    public void packRle3() throws IOException {
        PackRle.packing("test/resources/input.txt","test/resources/rletest3.txt");
        PackRle.unpacking("test/resources/rletest3.txt","test/resources/output.txt");
        File expected = new File("test/resources/input.txt");
        File actual = new File("test/resources/output.txt");
        assertTrue(FileUtils.contentEquals(expected, actual));
    }

}
