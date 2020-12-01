package Entities;

        import java.util.ArrayList;

public abstract class Video {
    String title;
    String videoType;
    int year;
    ArrayList<String> cast;
    ArrayList<String> genres;
    ArrayList<Double> ratings;
    double totalRating;
    int totalDuration;
    int contorView = 0;
    int contorFavorite = 0;

    public Video(String title, int year, ArrayList<String> cast, ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.ratings = new ArrayList<>();
    }

    public Video (Video video) {
        this.title = video.title;
        this.videoType = video.videoType;
        this.year = video.year;
        this.cast = video.cast;
        this.genres = video.genres;
        this.ratings = new ArrayList<>(video.ratings);
        this.totalDuration = video.totalDuration;
    }
    @Override
    public boolean equals(Object object) {
        return this.title.equals(((Video)object).title);
    }
    public abstract Video clone();

    public abstract void setRating(int c, double rating, double rating2);
    public abstract double setUserRating(int c, double rating);
    public abstract int getTotalDuration();
}
