package app;

import java.io.IOException;

import fileio.ActionInputData;
import fileio.JsonHolyWriter;

public final class Command {
    private final AppHandler appHandler;

    public Command(final AppHandler appHandler) {
        this.appHandler = appHandler;

    }

    /**
     * Runs the specified action.
     * Writes the result in a JSON object.
     *
     * @param actionData
     */
    public void run(final ActionInputData actionData) {
        ActionStatus status;
        String type = actionData.getType();
        String username = actionData.getUsername();
        String title = actionData.getTitle();
        double grade = actionData.getGrade();

        switch (type) {
        case "view":
            status = appHandler.view(username, title);
            break;
        case "favorite":
            status = appHandler.favorite(username, title);
            break;
        case "rating":
            status = appHandler.rating(username, title, actionData.getSeasonNumber(), grade);
            break;
        default:
            // Should we do something else?
            System.out.println("Unknown type");
            return;
        }

        String message = "";

        switch (status) {
        case SUCCESS_VIEW:
            message = "success -> " + title + " was viewed with total views of "
                    + appHandler.getUserViews(username, title);
            break;
        case ALREADY_FAVORITE:
            message = "error -> " + title + " is already in favourite list";
            break;
        case ALREADY_RATED:
            message = "error -> " + title + " has been already rated";
            break;
        case INVALID_FAVORITE:
            message = "error -> " + title + " is not seen";
            break;
        case INVALID_RATING:
            message = "error -> " + title + " is not seen";
            break;
        case SUCCESS_FAVORITE:
            message = "success -> " + title + " was added as favourite";
            break;
        case SUCCESS_RATING:
            message = "success -> " + title + " was rated with " + grade + " by " + username;
            break;
        default:
            break;
        }

        // The holy way of writing json

        final JsonHolyWriter holyWriter = appHandler.getHolyWriter();
        try {
            holyWriter.holyWriteFile(actionData.getActionId(), "message", message);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
