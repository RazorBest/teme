package entertainment;

import java.util.ArrayList;

import fileio.MovieInputData;

public final class Movie extends Video {
    private final int duration;
    private double totalGrade = 0;
    private int ratingCount = 0;

    public Movie(final String title, final int year, final ArrayList<String> cast,
            final ArrayList<String> genres, final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public Movie(final MovieInputData movieData) {
        super(movieData.getTitle(), movieData.getYear(), movieData.getCast(),
                movieData.getGenres());
        this.duration = movieData.getDuration();
    }

    @Override
    public void addRating(final int season, final double grade) {
        totalGrade += grade;
        ratingCount++;
    }

    @Override
    public double getRating() {
        if (ratingCount == 0) {
            return 0;
        }
        return totalGrade / ratingCount;
    }

    @Override
    public int getDuration() {
        return duration;
    }
}
