package Entities;

import java.util.ArrayList;

public class Movie extends Video {
    double rating;

    public Movie(final String title, final int year, final ArrayList<String> cast, final ArrayList<String> genre,
            final int duration, final String videoType) {
        super(title, year, cast, genre);
        this.totalDuration = duration;
        this.videoType = videoType;
    }

    public Movie(final Video video) {
        super(video);
    }

    @Override
    public Video clone() {
        return new Movie(this);
    }

    @Override
    public void setRating(final int redundant, final double rating, final double redundant1) {
        this.ratings.add(rating);
        this.totalRating = 0;
        for (final Double aDouble : ratings) {
            this.totalRating += aDouble;
        }
        this.totalRating /= ratings.size();
    }

    @Override
    public double setUserRating(final int c, final double rating) {
        if (this.rating == 0) {
            this.rating = rating;
            return this.rating;
        } else {
            return -1;
        }
    }

    @Override
    public int getTotalDuration() {
        return totalDuration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= " + super.title + "year= " + super.year + "duration= " + totalDuration
                + "cast {" + super.cast + " }\n" + "genres {" + super.genres + " }\n " + "viewCount= "
                + super.contorView + "\n";
    }
}
