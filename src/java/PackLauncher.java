import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


public final class PackLauncher {

    @Option(name = "-z", usage = "Packing", forbids = {"-u"})
    private boolean pack = false;

    @Option(name = "-u", usage = "Unpacking", forbids = {"-z"})
    private boolean unpack = false;

    @Option(name = "-out", usage = "Output to this file")
    private static String outputName;

    @Argument(required = true, usage = "InputName")
    private String inputName;

    public static void main(String[] args) {
        new PackLauncher().launch(args);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java - jar pack-rle -z Packing -u Unpacking -out Output to this file InputName");
            parser.printUsage(System.err);
            return;
        }
        if (outputName == null) {
            if (pack) outputName = inputName + ".rle";
            else {
                if (outputName.matches(".rle")) outputName = inputName.substring(0,outputName.length() - 4);
                else if (pack) outputName = inputName + ".rle";
            }
        }
        PackRle packer = new PackRle(inputName, outputName);
        if (pack) packer.packing(inputName, outputName);
        else packer.unpacking(inputName, outputName);
        System.out.println(pack ? "Pack successful" : "Unpack successful");
    }

}
