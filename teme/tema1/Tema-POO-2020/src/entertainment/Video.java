package entertainment;

import java.util.ArrayList;

public abstract class Video {
    private final String title;
    private final int year;
    private final String[] cast;
    private final String[] genres;
    
    private int views = 0;
    private int favorite = 0;
    private double total_grade = 0;
    private int n_ratings = 0;

    public Video(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast.toArray(new String[0]);
        this.genres = genres.toArray(new String[0]);
    }
    
    public void addView() {
    	views++;
    }
    
    public void addFavorite() {
    	favorite++;
    }
    
    public void addRating(final double grade) {
    	total_grade += grade;
    	n_ratings++;
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

    public final String[] getGenres() {
        return genres;
    }
    
    public final int getViews() {
    	return views;
    }
}
