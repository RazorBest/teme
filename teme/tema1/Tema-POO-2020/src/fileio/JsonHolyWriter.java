package fileio;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/* The writer class isn't that helpful because it doesn't build the JsonArray. The Writer.writeFile() method is not static.
 * The Writer class is final. 
 * This means the Writer class is evil.
 * So we have to make another class that's holy. May God have mercy upon the souls of the ones 
 * that made the evil Writer class */
public class JsonHolyWriter {
	Writer writer;
	JSONArray jsonArray;
	
	public JsonHolyWriter(Writer writer) {
		this.writer = writer;
		jsonArray = new JSONArray();
		
	}
	
	public void holyWriteFile(final int id, final String field, final String message) throws IOException {
		JSONObject obj = writer.writeFile(id, field, message);
		jsonArray.add(obj);
	}
	
	public void closeJSON() {
		writer.closeJSON(jsonArray);
	}
}
