package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.JsonHolyWriter;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import query.QueryApp;

public final class AppHandler {
    private final User[] userArray;
    private final Map<String, User> userMap;
    private final Video[] videoArray;
    private final Map<String, Video> videoMap;
    private final Actor[] actorArray;
    private final Map<String, Actor> actorMap;

    private final Input input;
    private final JsonHolyWriter holyWriter;
    private final Command command;
    private final QueryApp query;
    private final Recommendation recommendation;

    public AppHandler(final Input input, final JsonHolyWriter holyWriter) {
        this.input = input;
        this.holyWriter = holyWriter;

        userArray = createUserArray();
        userMap = createUserMap();
        videoArray = createVideoArray();
        videoMap = createVideoMap();
        actorArray = createActorArray();
        actorMap = createActorMap();

        updateVideoViewCount();
        updateVideoFavoriteCount();

        command = new Command(this);
        query = new QueryApp(this, holyWriter);
        recommendation = new Recommendation(this, holyWriter);
    }

    private User[] createUserArray() {
        final List<UserInputData> userInputList = input.getUsers();

        final User[] userArray = new User[userInputList.size()];
        int i = 0;
        for (final UserInputData userData : userInputList) {
            userArray[i] = new User(userData);
            i++;
        }

        return userArray;
    }

    private Map<String, User> createUserMap() {
        // Might use LinkedHashMap
        final Map<String, User> userMap = new HashMap<String, User>();

        for (final User user : userArray) {
            userMap.put(user.getUsername(), user);
        }

        return userMap;
    }

    private Video[] createVideoArray() {
        final List<MovieInputData> movieInputList = input.getMovies();
        final List<SerialInputData> serialInputList = input.getSerials();
        Video[] videoArray;

        videoArray = new Video[movieInputList.size() + serialInputList.size()];
        int i = 0;
        for (final MovieInputData movieData : movieInputList) {
            videoArray[i] = new Movie(movieData);
            i++;
        }

        for (final SerialInputData serialData : serialInputList) {
            videoArray[i] = new Serial(serialData);
            i++;
        }

        return videoArray;
    }

    private Map<String, Video> createVideoMap() {
        // Might use LinkedHashMap
        final Map<String, Video> videoMap = new HashMap<String, Video>();

        for (final Video video : videoArray) {
            videoMap.put(video.getTitle(), video);
        }

        return videoMap;
    }

    private Actor[] createActorArray() {
        final List<ActorInputData> actorInputList = input.getActors();

        final Actor[] actorArray = new Actor[actorInputList.size()];
        int i = 0;
        for (final ActorInputData actorData : actorInputList) {
            actorArray[i] = new Actor(actorData);
            i++;
        }

        return actorArray;
    }

    private Map<String, Actor> createActorMap() {
        // Might use LinkedHashMap
        final Map<String, Actor> actorMap = new HashMap<String, Actor>();

        for (final Actor actor : actorArray) {
            actorMap.put(actor.getName(), actor);
        }

        return actorMap;
    }

    private void updateVideoViewCount() {
        for (final User user : userArray) {
            for (final Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                final String title = entry.getKey();
                if (videoMap.containsKey(title)) {
                    videoMap.get(title).addViews(entry.getValue());
                }
            }
        }
    }

    private void updateVideoFavoriteCount() {
        for (final User user : userArray) {
            for (final String title : user.getFavoriteMovies()) {
                if (videoMap.containsKey(title)) {
                    videoMap.get(title).addFavorite();
                }
            }
        }
    }

    /**
     * Iterate through the action array and run each action
     * It will use different action handlers depending on the action type
     * The action type can be command, query or recommendation
     *
     */
    public void runActions() {
        for (final ActionInputData actionData : input.getCommands()) {
            final String actionType = actionData.getActionType();
            if (actionType.equals("command")) {
                command.run(actionData);
            } else if (actionType.equals("query")) {
                query.run(actionData);
            } else if (actionType.equals("recommendation")) {
                recommendation.run(actionData);
            }
        }
    }

    /**
     * Applies the "view" action to the video and user DB
     *
     * @param username  the name of the user that views the video
     * @param title     the title of the video
     * @return          an enum value that indicates the validation outcome of the action.
     *                  It is always SUCCESS_VIEW because it does
     *                  not need to be validated.
     */
    public ActionStatus view(final String username, final String title) {
        final User user = userMap.get(username);
        final Video video = videoMap.get(title);

        user.addView(title);
        video.addView();

        return ActionStatus.SUCCESS_VIEW;
    }

    /**
     * Applies the "favorite" action to the video and user DB
     *
     * @param username  the name of the user that marks the video as favorite
     * @param title     the title of the video
     * @return          an enum value that indicates the validation outcome of the action.
     *                  The validation is done by the addFavorite method in User.
     */
    public ActionStatus favorite(final String username, final String title) {
        final User user = userMap.get(username);
        ActionStatus status;

        status = user.addFavorite(title);

        if (status == ActionStatus.SUCCESS_FAVORITE) {
            final Video video = videoMap.get(title);
            video.addFavorite();
        }

        return status;
    }

    /**
     * Applies the rating action to the video, user and actor DB.
     * In the user object, it stores the video in a set.
     * In the video object, it stores only the grade.
     * In the actor objects, it stores a pair with the video and the grade.
     *
     * @param username  the name of the user that rates the video
     * @param title     the title of the video
     * @param season    the season of the video; by convention, it's 0 for movies
     * @param grade     the value of the rating given by the user
     * @return          an enum value that indicates the validation outcome of the action.
     *                  The validation is done by the addRating method in User.
     */
    public ActionStatus rating(final String username, final String title,
            final int season, final double grade) {
        final User user = userMap.get(username);
        ActionStatus status;

        status = user.addRating(title, season);

        if (status == ActionStatus.SUCCESS_RATING) {
            final Video video = videoMap.get(title);
            video.addRating(season, grade);

            final double rating = video.getRating();
            final String[] cast = video.getCast();
            for (final String name : cast) {
                if (actorMap.containsKey(name)) {
                    actorMap.get(name).addRating(title, season, rating);
                }
            }

        }

        return status;
    }

    /**
     * Validates the recommendation action by checking the user DB.
     * It is called by the Recommendation class.
     *
     * @param type      the type of recommendation
     * @param username  the name of the user that requests the recommendation
     * @return          an enum value that indicates the validation outcome of the action.
     *                  If the user is premium, it is always SUCCESS
     *                  If the user is standard and the type is "popular", "favorite" or "search",
     *                  it returns an error status (depending on the type)
     *                  Otherwise, it returns SUCCESS
     */
    public ActionStatus verifyRecommendation(final String type, final String username) {
        final User user = userMap.get(username);

        if (user.getSubscriptionType().equals("PREMIUM")) {
            return ActionStatus.SUCCESS;
        }

        switch (type) {
        case "popular":
            return ActionStatus.POPULAR_NOT_PREMIUM;
        case "favorite":
            return ActionStatus.FAVORITE_NOT_PREMIUM;
        case "search":
            return ActionStatus.SEARCH_NOT_PREMIUM;
        default:
            return ActionStatus.SUCCESS;
        }
    }

    /**
     * @param username  the name of an user
     * @param title     the name of a video
     * @return          the number of views that the user has for the video
     */
    public int getUserViews(final String username, final String title) {
        return userMap.get(username).getHistory().get(title);
    }

    public JsonHolyWriter getHolyWriter() {
        return holyWriter;
    }

    public User[] getUserArray() {
        return userArray;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Video[] getVideoArray() {
        return videoArray;
    }

    public Map<String, Video> getVideoMap() {
        return videoMap;
    }

    public Actor[] getActorArray() {
        return actorArray;
    }
}
