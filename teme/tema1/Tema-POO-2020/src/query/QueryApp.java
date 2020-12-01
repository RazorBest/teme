package query;

import java.io.IOException;
import java.util.ArrayList;

import app.AppHandler;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.JsonHolyWriter;

public final class QueryApp {
    private final AppHandler app;
    private final UserQueryHandler userQuery;
    private final VideoQueryHandler videoQuery;
    private final VideoQueryHandler movieQuery;
    private final VideoQueryHandler serialQuery;
    private final ActorQueryHandler actorQuery;

    private final JsonHolyWriter writer;

    private static Video[] filterMovies(final Video[] videos) {
        final ArrayList<Video> filtered = new ArrayList<Video>();

        for (final Video video : videos) {
            if (Movie.class.isInstance(video)) {
                filtered.add(video);
            }
        }

        return filtered.toArray(new Video[0]);
    }

    private static Video[] filterSerials(final Video[] videos) {
        final ArrayList<Video> filtered = new ArrayList<Video>();

        for (final Video video : videos) {
            if (Serial.class.isInstance(video)) {
                filtered.add(video);
            }
        }

        return filtered.toArray(new Video[0]);
    }

    public QueryApp(final AppHandler app, final JsonHolyWriter writer) {
        this.app = app;
        this.writer = writer;

        userQuery = new UserQueryHandler(app.getUserArray());
        videoQuery = new VideoQueryHandler(app.getVideoArray());
        movieQuery = new VideoQueryHandler(filterMovies(app.getVideoArray()));
        serialQuery = new VideoQueryHandler(filterSerials(app.getVideoArray()));
        actorQuery = new ActorQueryHandler(app.getActorArray());
    }

    /**
     * Apply the query by running the respective QueryHandler.
     * The QueryHandlers are:
     *      - userQuery
     *      - videoQuery (for movies and serials)
     *      - moiveQuery
     *      - serialQuery
     *      - actorQuery
     *
     * @param actionData    data about the action
     */
    public void run(final ActionInputData actionData) {
        String message = "";

        if (actionData.getObjectType().equals("movies")) {
            message = movieQuery.run(actionData);
        } else if (actionData.getObjectType().equals("shows")) {
            message = serialQuery.run(actionData);
        } else if (actionData.getObjectType().equals("actors")) {
            message = actorQuery.run(actionData);
        } else if (actionData.getObjectType().equals("users")) {
            message = userQuery.run(actionData);
        }

        try {
            writer.holyWriteFile(actionData.getActionId(), "message", message);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
