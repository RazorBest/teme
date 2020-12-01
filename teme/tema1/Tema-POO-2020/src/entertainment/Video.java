package entertainment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Video {
    private final String title;
    private final int year;
    private final String[] cast;
    private final Set<String> genres = new HashSet<String>();

    private int views = 0;
    private int favorite = 0;

    public Video(final String title, final int year, final ArrayList<String> cast,
            final ArrayList<String> genresList) {
        this.title = title;
        this.year = year;
        this.cast = cast.toArray(new String[0]);
        for (final String genre : genresList) {
            genres.add(genre);
        }
    }

    /**
     * Stores the rating given by a user for a video.
     *
     * @param season    the season of the video; by convention, it is 0 for movies
     * @param grade     the rating given by a user
     */
    public abstract void addRating(int season, double grade);

    /**
     * @return  the average rating given by all the users.
     *          The ratings are given through the addRating method
     */
    public abstract double getRating();

    /**
     * Increases the number of views for the video.
     */
    public final void addView() {
        views++;
    }

    /**
     * Adds an amount of views to the video.
     *
     * @param views     the number of views to be added
     */
    public final void addViews(final int viewsToAdd) {
        this.views += viewsToAdd;
    }

    /**
     * Increases the number of favorite caount for the video.
     */
    public final void addFavorite() {
        favorite++;
    }

    /**
     *
     * @param genre
     * @return          true, if the movie has the given genre
     *                  false, otherwise
     */
    public final boolean hasGenre(final String genre) {
        return genres.contains(genre);
    }

    /**
     *
     * @return  the duration of the movie
     *          if it's a serial, the sum of the durations of all seasons
     */
    public abstract int getDuration();

    @Override
    public final String toString() {
        return title;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final String[] getCast() {
        return cast;
    }

    public final int getViews() {
        return views;
    }

    public final int getFavorite() {
        return favorite;
    }

    public final Set<String> getGenres() {
        return genres;
    }
}
