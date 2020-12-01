package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import app.AppHandler;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.JsonHolyWriter;
import fileio.Writer;

/**
 * The entry point to this homework. It runs the checker that tests your
 * implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        final File directory = new File(Constants.TESTS_PATH);
        final Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        final File outputDirectory = new File(Constants.RESULT_PATH);

        final Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (final File file : Objects.requireNonNull(directory.listFiles())) {

            final String filepath = Constants.OUT_PATH + file.getName();
            final File out = new File(filepath);
            final boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        final Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1, final String filePath2) throws IOException {
        final InputLoader inputLoader = new InputLoader(filePath1);
        final Input input = inputLoader.readData();

        final Writer fileWriter = new Writer(filePath2);
        final JsonHolyWriter holyWriter = new JsonHolyWriter(fileWriter);

        System.out.println("Input: " + filePath1);
        System.out.println("Output: " + filePath2);

        final AppHandler appHandler = new AppHandler(input, holyWriter);
        appHandler.runActions();

        holyWriter.closeJSON();
    }
}
