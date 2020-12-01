package actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fileio.ActorInputData;

public final class Actor {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    private Map<String, Double> videoRatings;
    private double rating;
    private boolean ratingChanged = true;
    private int awardCount = 0;

    public Actor(final String name, final String careerDescription,
            final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;

        for (final Integer awardTypeCount : awards.values()) {
            awardCount += awardTypeCount;
        }

        videoRatings = new HashMap<String, Double>();
    }

    public Actor(final ActorInputData actorData) {
        this.name = actorData.getName();
        this.careerDescription = actorData.getCareerDescription();
        this.filmography = actorData.getFilmography();
        this.awards = actorData.getAwards();

        for (final Integer awardTypeCount : awards.values()) {
            awardCount += awardTypeCount;
        }

        videoRatings = new HashMap<String, Double>();

        videoRatings = new HashMap<String, Double>();
    }

    /**
     * Stores the rating of a video that the this actor has participated in.
     * Also, updates the average rating of the actor.
     * This method is called after a user gives a rating to a movie.
     * The rating of the movie updates and this method is called with the respective rating.
     *
     * @param title     The title of the video
     * @param season    The season of the video. By convention, it is 0 for movies
     * @param movieRating    The value of the average rating of the movie.
     */
    public void addRating(final String title, final int season, final double movieRating) {
        videoRatings.put(title + season, movieRating);

        int ratingCount = 0;
        this.rating = 0;
        for (Double ratingValue : videoRatings.values()) {
            this.rating += ratingValue;
            ratingCount++;
        }

        this.rating = this.rating / ratingCount;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getAwardCount() {
        return awardCount;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }
}
