package main;

import Entities.Action;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        System.out.println(filePath1);
        Action action = new Action(input, fileWriter, arrayResult);
        for (ActionInputData command : input.getCommands()) {
            if (command.getActionType().equals("command")) {
                if (command.getType().equals("favorite")) {
                    action.setFavorite(command.getTitle(),
                            command.getUsername(), command.getActionId());
                }
                if (command.getType().equals("view")) {
                    action.setView(command.getTitle(),
                            command.getUsername(), command.getActionId());
                }
                if (command.getType().equals("rating")) {
                    action.setRating(command.getTitle(), command.getGrade(),
                            command.getSeasonNumber(),
                            command.getUsername(), command.getActionId());
                }
            }
            if (command.getActionType().equals("query")) {
                if (command.getObjectType().equals("actors")) {
                    if (command.getCriteria().equals("average")) {
                        action.Average(command.getNumber(), command.getSortType(),
                                command.getActionId());
                    }
                    if (command.getCriteria().equals("awards")) {
                        action.Awards(command.getFilters().get(3), command.getSortType(),
                                command.getActionId());
                    }
                    if (command.getCriteria().equals("filter_description")) {
                        action.FilterDescription(command.getFilters().get(2),
                                command.getSortType(), command.getActionId());
                    }
                }
                if (command.getCriteria().equals("ratings")) {
                    int yearFileter;
                    if (command.getFilters().get(0).get(0) == null) {
                        yearFileter = 0;
                    } else {
                        yearFileter = Integer.parseInt(command.getFilters().get(0).get(0));
                    }
                    action.Rating(command.getSortType(), yearFileter,
                            command.getNumber(), command.getFilters().get(1).get(0),
                            command.getObjectType(), command.getActionId());
                }
                if (command.getCriteria().equals("favorite")) {
                    int yearFileter;
                    if (command.getFilters().get(0).get(0) == null) {
                        yearFileter = 0;
                    } else {
                        yearFileter = Integer.parseInt(command.getFilters().get(0).get(0));
                    }
                    action.Favorite(command.getSortType(), yearFileter,
                            command.getNumber(), command.getFilters().get(1).get(0),
                            command.getObjectType(), command.getActionId());
                }
                if (command.getCriteria().equals("longest")) {
                    int yearFileter;
                    if (command.getFilters().get(0).get(0) == null) {
                        yearFileter = 0;
                    } else {
                        yearFileter = Integer.parseInt(command.getFilters().get(0).get(0));
                    }
                    action.Longest(command.getSortType(), yearFileter,
                            command.getNumber(), command.getFilters().get(1).get(0),
                            command.getObjectType(), command.getActionId());
                }
                if (command.getCriteria().equals("most_viewed")) {
                    int yearFileter;
                    if (command.getFilters().get(0).get(0) == null) {
                        yearFileter = 0;
                    } else {
                        yearFileter = Integer.parseInt(command.getFilters().get(0).get(0));
                    }
                    action.MostViewed(command.getSortType(), yearFileter,
                            command.getNumber(), command.getFilters().get(1).get(0),
                            command.getObjectType(), command.getActionId());
                }
                if (command.getObjectType().equals("users")
                        && command.getCriteria().equals("num_ratings")) {
                    action.NumberOfRating(command.getSortType(),
                            command.getNumber(),
                            command.getActionId());
                }
            }
            if (command.getActionType().equals("recommendation")) {
                if (command.getType().equals("standard")) {
                    action.Standard(command.getUsername(),
                            command.getActionId());
                }
                if (command.getType().equals("best_unseen")) {
                    action.BestUnseen(command.getUsername(),
                            command.getActionId());
                }
                if (command.getType().equals("popular")) {
                    action.PremiumPopular(command.getUsername(),
                            command.getActionId());
                }
                if (command.getType().equals("favorite")) {
                    action.PremiumFavorite(command.getUsername(),
                            command.getActionId());
                }
                if (command.getType().equals("search")) {
                    action.PremiumSearch(command.getUsername(),
                            command.getGenre(), command.getActionId());
                }
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
