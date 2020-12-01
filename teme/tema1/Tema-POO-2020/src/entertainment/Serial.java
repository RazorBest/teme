package entertainment;

import java.util.ArrayList;
import java.util.List;

import fileio.SerialInputData;

public final class Serial extends Video {
    private final ArrayList<Season> seasons;
    private final int duration;

    public Serial(final String title, final int year, final ArrayList<String> cast,
            final ArrayList<String> genres, final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.seasons = seasons;

        int serialDuration = 0;
        for (final Season season : seasons) {
            serialDuration += season.getDuration();
        }
        this.duration = serialDuration;
    }

    public Serial(final SerialInputData serialData) {
        super(serialData.getTitle(), serialData.getYear(),
                serialData.getCast(), serialData.getGenres());
        this.seasons = serialData.getSeasons();

        int serialDuration = 0;
        for (final Season season : seasons) {
            serialDuration += season.getDuration();
        }
        this.duration = serialDuration;
    }

    @Override
    public void addRating(final int seasonNumber, final double grade) {
        final Season season = seasons.get(seasonNumber - 1);
        final List<Double> grades = season.getRatings();

        grades.add(grade);
    }

    @Override
    public double getRating() {
        double rating = 0;

        for (final Season season : seasons) {
            double totalGrade = 0;
            for (final Double grade : season.getRatings()) {
                totalGrade += grade;
            }
            if (season.getRatings().size() > 0) {
                rating += totalGrade / season.getRatings().size();
            }
        }

        return rating / seasons.size();
    }

    @Override
    public int getDuration() {
        return duration;
    }
}
