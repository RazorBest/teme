package checker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import common.Constants;

/**
 * Checker to verify the coding style
 */
public final class Checkstyle {
    /**
     * DO NOT MODIFY
     */
    public void testCheckstyle() {
        final ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", Constants.JAR_PATH,
                "-c", Constants.XML_PATH, "./");

        processBuilder.redirectErrorStream(true);
        final File log = new File(Constants.CHECKSTYLE_FILE);
        processBuilder.redirectOutput(log);

        try {
            final Process process = processBuilder.start();
            process.waitFor();

            final Path path = Paths.get(Constants.CHECKSTYLE_FILE);
            final long lineCount = Files.lines(path).count();

            long errors = 0;
            if (lineCount > Constants.MIN_LINES) {
                errors = lineCount - Constants.NUM_CHECK_INFO;
            }
            System.out.println("-----------------------------");
            System.out.println(
                    "Checkstyle: " + ((errors <= Constants.MIN_CHECKSTYLE_ERR) ? "Ok" : "Failed"));
            System.out.println("Checkstyle errors: " + errors);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
