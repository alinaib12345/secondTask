import org.kohsuke.args4j.*;

public final class PackLauncher {

    @Option(name = "-z", usage = "packing file", forbids = {"-u"})
    private boolean pack = false;

    @Option(name = "-u", usage = "unpacking file", forbids = {"-z"})
    private boolean unpack = false;

    @Option(name = "-out", usage = "output to this file (default: inputname.txt)")
    private static String outputName = null;

    @Argument(required = true, index = 1)
    private String inputName;

    public static void main(String[] args) {
        new PackLauncher().launch(args);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if ((!pack && !unpack) || (pack && unpack) || args[0] != ("Pack-rle")) {
                System.err.println("Error entering arguments");
                throw new IllegalArgumentException("Illegal arguments");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
        }
        PackRle packRle = new PackRle(pack, inputName, outputName);
        packRle.packRle();
    }

}
