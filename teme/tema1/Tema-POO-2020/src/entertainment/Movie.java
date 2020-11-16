package entertainment;

import java.util.ArrayList;

import fileio.MovieInputData;

public final class Movie extends Video {

	private final int duration;
	
	public Movie(final String title, final int year, 
			final ArrayList<String> cast, final ArrayList<String> genres, 
			final int duration) {
		super(title, year, cast, genres);
		this.duration = duration;
	}
	
	public Movie(MovieInputData movieData) {
		super(movieData.getTitle(), movieData.getYear(), movieData.getCast(), movieData.getGenres());
		this.duration = movieData.getDuration();
	}
	
	public final int getDuration() {
		return duration;
	}
}
