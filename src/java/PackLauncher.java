import org.kohsuke.args4j.*;
import java.util.ArrayList;
import java.util.List;


public final class PackLauncher {

    @Option(name = "-z", usage = "packing file", forbids = {"-u"})
    private boolean pack = false;

    @Option(name = "-u", usage = "unpacking file", forbids = {"-z"})
    private boolean unpack = false;

    @Option(name = "-out", usage = "output to this file (default: inputname.txt)")
    private static String outputName = "";

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static void main(String[] args) {
        new PackLauncher().launch(args);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if ((!pack && !unpack) || (pack && unpack) || !arguments.get(0).equals("Pack-rle")) {
                System.err.println("Error entering arguments");
                throw new IllegalArgumentException("Illegal arguments");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
        }
        String input = arguments.get(1);
        PackRle packRle = new PackRle(pack, input, outputName);
        packRle.packRle();
    }

}
