import app.Handler;
import io.EconomyInputReader;
import io.EconomyWriter;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() {
    }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing
     *                   JSON
     */
    public static void main(final String[] args) throws Exception {
        final String inputPath = args[0];
        final String outputPath = args[1];

        System.out.println(inputPath);
        System.out.println(outputPath);

        final EconomyInputReader reader = new EconomyInputReader();
        final EconomyWriter writer = new EconomyWriter(outputPath);

        reader.read(inputPath);

        final Handler handler = Handler.getInstance();

        handler.setReader(reader);
        handler.setWriter(writer);
        handler.runSimulation();

        writer.flush();

    }
}
