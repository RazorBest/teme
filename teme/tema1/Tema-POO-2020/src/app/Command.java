package app;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.Constants;
import fileio.ActionInputData;
import fileio.JsonHolyWriter;

public class Command {
	final AppHandler appHandler;
	
	final int id;
	final String type;
	final String username;
	final String title;
	final double grade;
	
	public Command(AppHandler appHandler, ActionInputData actionData) {
		this.appHandler = appHandler;
		
		id = actionData.getActionId();
		type = actionData.getType();
		username = actionData.getUsername();
		title = actionData.getTitle();
		
		if (type.equals("rating")) {
			grade = actionData.getGrade();
		} else {
			grade = 0;
		}
	}
	
	//  This seems a little redundant
	// I might make child classes for every type of command
	public void run() {
		CommandStatus status;
		
		switch(type) {
			case "view": 	
				status = appHandler.view(username, title);
				break;
			case "favorite": 	
				status = appHandler.favorite(username, title);
				break;
			case "rating": 	
				status = appHandler.rating(username, title, grade);
				break;
			default:
				// Should I do something else?
				System.out.println("Unknown type");
				return;
		}
		
		String message = "";
		
		switch(status) {
			case SUCCESS_VIEW:
				message = "success -> " + title + " was viewed with total views of " + appHandler.getViews(title);
				break;
			case ALREADY_FAVORITE:
				message = "error -> " + title + " is already in favourite list";
				break;
			case ALREADY_RATED:
				break;
			case INVALID_FAVORITE:
				message = "error -> " + title + " is not seen";
				break;
			case INVALID_RATING:
				message = "error -> " + title + " is not seen";
				break;
			case SUCCESS_FAVORITE:
				message = "success -> " + title + " was added as favourite";
				break;
			case SUCCESS_RATING:
				message = "success -> " + title + " was rated with " + grade + " by " + username;
				break;
			default:
				break;
		}
		
		// The holy way of writing json
		
		JsonHolyWriter holyWriter = appHandler.getHolyWriter();
		try {
			holyWriter.holyWriteFile(id, "message", message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
