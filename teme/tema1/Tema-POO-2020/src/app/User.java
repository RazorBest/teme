package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fileio.UserInputData;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    // Might use a hashtable?
    private final Set<String> favoriteMovies;
    private final Set<String> ratedMovies;

    private int ratings = 0;

    public User(final String username, final String subscriptionType,
            final Map<String, Integer> history, final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = new HashSet<String>();
        for (final String title : favoriteMovies) {
            this.favoriteMovies.add(title);
        }

        this.ratedMovies = new HashSet<String>();
    }

    public User(final UserInputData userData) {
        username = userData.getUsername();
        subscriptionType = userData.getSubscriptionType();
        history = userData.getHistory();
        favoriteMovies = new HashSet<String>();
        for (final String title : userData.getFavoriteMovies()) {
            favoriteMovies.add(title);
        }

        this.ratedMovies = new HashSet<String>();
    }

    /**
     * Adds a view to the corresponding video in the hashmap
     *
     * @param title     the title of the video that has been viewed
     */
    public void addView(final String title) {
        history.putIfAbsent(title, 0);
        final int views = history.get(title);
        history.put(title, views + 1);
    }

    /**
     *
     * @param title     the title of the video that is checked
     * @return          true, if the video was marked as favorite by the user
     *                  false, otherwise
     */
    private boolean isFavorite(final String title) {
        return favoriteMovies.contains(title);
    }

    /**
     * Tries to apply the "favorite" action to the movie.
     *
     * @param title     the title of the movie
     * @return          an enum value that indicates the validation outcome of the action.
     *                  INVALID_FAVORITE, if the user didn't view the video
     *                  ALREADY_FAVORITE, if this method was already called with the same params
     *                  SUCCESS_FAVORITE, otherwise
     */
    public ActionStatus addFavorite(final String title) {
        if (!history.containsKey(title)) {
            return ActionStatus.INVALID_FAVORITE;
        }

        if (isFavorite(title)) {
            return ActionStatus.ALREADY_FAVORITE;
        }

        favoriteMovies.add(title);

        return ActionStatus.SUCCESS_FAVORITE;
    }

    /**
     * Checks if a video has been rated.
     *
     * @param   The title of the video. By convention, for a serial, the title is name|season_number
     * @return  true if the video has already been rated
     *          false, otherwise
     */
    private boolean isRated(final String title) {
        return ratedMovies.contains(title);
    }

    /**
     * Tries to apply the rating action to the movie.
     * Stores the video that it is rated.
     *
     * @param title     The title of the video
     * @param season    The season of the video. By convention, it is 0 for movies.
     * @return          an enum value that indicates the validation outcome of the action.
     *                  INVALID_RATING, if the user didn't view the video
     *                  ALREADY_RATED, if this method was already called with the same params
     *                  SUCCESS_RATING, otherwise
     */
    public ActionStatus addRating(final String title, final int season) {
        if (!history.containsKey(title)) {
            return ActionStatus.INVALID_RATING;
        }
        if (isRated(title + season)) {
            System.out.println("abc");
            return ActionStatus.ALREADY_RATED;
        }

        ratings++;
        ratedMovies.add(title + season);

        return ActionStatus.SUCCESS_RATING;
    }

    @Override
    public String toString() {
        return username;
    }

    public int getRatingCount() {
        return ratings;
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public Set<String> getFavoriteMovies() {
        return favoriteMovies;
    }
}
