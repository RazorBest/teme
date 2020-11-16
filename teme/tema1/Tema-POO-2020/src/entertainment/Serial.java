package entertainment;

import java.util.ArrayList;

import fileio.SerialInputData;

public class Serial extends Video {
	private final int numberOfSeasons;
	private final ArrayList<Season> seasons;
	
	public Serial(String title, int year, ArrayList<String> cast, ArrayList<String> genres, int numberOfSeasons, ArrayList<Season> seasons) {
		super(title, year, cast, genres);
		this.numberOfSeasons = numberOfSeasons;
		this.seasons = seasons;
	}

	public Serial(SerialInputData serialData) {
		super(serialData.getTitle(), serialData.getYear(), serialData.getCast(), serialData.getGenres());
		this.numberOfSeasons = serialData.getNumberSeason();
		this.seasons = serialData.getSeasons();
	}
}
