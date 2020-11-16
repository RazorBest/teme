package app;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.json.simple.JSONArray;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Input;
import fileio.JsonHolyWriter;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;

public class AppHandler {
	private final User[] userArray;
	private final Map<String, User> userMap;
	private final Video[] videoArray;
	private final Map<String, Video> videoMap;
	
	private Input input;
	JsonHolyWriter holyWriter;
	private Queue<Command> commandQueue;
	
	public AppHandler(Input input, JsonHolyWriter holyWriter) {
		this.input = input;
		this.holyWriter = holyWriter;
		
		userArray = createUserArray();
		userMap = createUserMap();
		videoArray = createVideoArray();
		videoMap = createVideoMap();
		
		commandQueue = new LinkedList<Command>();
	}
	
	private User[] createUserArray() {
		List<UserInputData> userInputList = input.getUsers();
		
		User[] userArray = new User[userInputList.size()];
		int i = 0;
		for (UserInputData userData : userInputList) {
			userArray[i] = new User(userData);
			i++;
		}
		
		return userArray;
	}
	
	private Map<String, User> createUserMap() {
		// Might use LinkedHashMap
		Map<String, User> userMap = new HashMap<String, User>();
		
		for (User user : userArray) {
			userMap.put(user.getUsername(), user);
		}
		
		return userMap;
	}
	
	private Video[] createVideoArray() {
		List<MovieInputData> movieInputList = input.getMovies();
		List<SerialInputData> serialInputList = input.getSerials();
		Video[] videoArray;
	
		videoArray = new Video[movieInputList.size() + serialInputList.size()]; 
		int i = 0;
		for (MovieInputData movieData : movieInputList) {
			videoArray[i] = new Movie(movieData);
			i++;
		}
		
		for (SerialInputData serialData : serialInputList) {
			videoArray[i] = new Serial(serialData);
			i++;
		}
		
		return videoArray;
	}
	
	private Map<String, Video> createVideoMap() {
		// Might use LinkedHashMap
		Map<String, Video> videoMap = new HashMap<String, Video>();
		
		for (Video video : videoArray) {
			videoMap.put(video.getTitle(), video);
		}
		
		return videoMap;
	}

	public void runActions() {
		for (ActionInputData actionData : input.getCommands()) {
			String actionType = actionData.getActionType();
			if (actionType.equals("command")) {
				commandQueue.add(new Command(this, actionData));
			} else if (actionType.equals("query")) {
				runCommandQueue();
				
				
				
				//runQuery(actionData);
			}
		}
		
		runCommandQueue();
	}

	public void runCommandQueue() {
		while (!commandQueue.isEmpty()) {
			commandQueue.poll().run();
		}
	}
	
	private void runQuery(ActionInputData actionData) {
		String objectType = actionData.getObjectType();
		int number = actionData.getNumber();
		String criteria = actionData.getCriteria();
		
		if (objectType == "movies") {
			if (criteria == "longest") {
				
			}
		}
	}
	
	public CommandStatus view(final String username, final String title) {
		User user = userMap.get(username);
		Video video = videoMap.get(title);
		
		user.addView(title);
		video.addView();
		
		return CommandStatus.SUCCESS_VIEW;
	}
	
	public CommandStatus favorite(final String username, final String title) {
		User user = userMap.get(username);
		CommandStatus status;
		
		status = user.addFavorite(title);
		
		if (status == CommandStatus.SUCCESS_FAVORITE) {
			Video video = videoMap.get(title);
			video.addFavorite();
		} else {
			// handle favorite error
		}
		
		return status;
	}
	
	public CommandStatus rating(final String username, final String title, final double grade) {
		User user = userMap.get(username);
		CommandStatus status;
		
		status = user.addRating(title);
		
		if (status == CommandStatus.SUCCESS_RATING) {
			Video video = videoMap.get(title);
			video.addRating(grade);
		} else {
			// handle rating error
		}
		
		return status;
	}
	
	public int getViews(String title) {
		return videoMap.get(title).getViews();
	}
	
	public JsonHolyWriter getHolyWriter() {
		return holyWriter;
	}
}
