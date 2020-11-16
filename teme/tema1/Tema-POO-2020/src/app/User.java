package app;
import java.util.ArrayList;
import java.util.Map;

import fileio.UserInputData;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    // Might use a hashtable?
    private final ArrayList<String> favoriteMovies;
    
    private final ArrayList<String> ratedMovies = new ArrayList<String>();
    private int ratings = 0;

    public User(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }
    
    public User(UserInputData userData) {
		username = userData.getUsername();
		subscriptionType = userData.getSubscriptionType();
		history = userData.getHistory();
		favoriteMovies = userData.getFavoriteMovies();
    }
    
    public void addView(final String title) {
    	history.putIfAbsent(title, 0);
    	int views = history.get(title);
    	history.put(title, views + 1);
    }
    
    private boolean isFavorite(final String title) {
    	return favoriteMovies.contains(title);
    }
    
    public CommandStatus addFavorite(final String title) {
    	if (!history.containsKey(title)) {
    		return CommandStatus.INVALID_FAVORITE;
    	}
    
    	if (isFavorite(title)) {
    		return CommandStatus.ALREADY_FAVORITE;
    	}
    	
    	favoriteMovies.add(title);
    	
    	return CommandStatus.SUCCESS_FAVORITE;
    }
    
    private boolean isRated(final String title) {
    	return ratedMovies.contains(title);
    }
    
    public CommandStatus addRating(final String title) {
    	if (!history.containsKey(title)) {
    		return CommandStatus.INVALID_RATING;
    	}
    	if(isRated(title)) {
    		return CommandStatus.ALREADY_RATED;
    	}
    	
    	ratings++;
    	
    	return CommandStatus.SUCCESS_RATING;
    }
    
    public String getUsername() {
    	return username;
    }
}
