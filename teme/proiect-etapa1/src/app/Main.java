package app;

import io.EconomyInputReader;
import io.EconomyWriter;

public class Main {

    public static void main(final String[] args) throws Exception {
        final String inputPath = args[0];
        final String outputPath = args[1];
        final EconomyInputReader reader = new EconomyInputReader();
        final EconomyWriter writer = new EconomyWriter(outputPath);

        System.out.println(inputPath);
        System.out.println(outputPath);

        reader.read(inputPath);

        final Handler handler = new Handler(reader, writer);

        handler.runSimulation();

        writer.flush();
    }
}
