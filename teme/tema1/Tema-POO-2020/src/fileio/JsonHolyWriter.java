package fileio;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/* The writer class isn't that helpful because it doesn't build the JsonArray.
 * The Writer.writeFile() method is not static.
 * The Writer class is final.
 * This means the Writer class is evil.
 * So we have to make another class that's holy. May God have mercy upon the souls of the ones
 * that made the evil Writer class
 * */
public final class JsonHolyWriter {
    private Writer writer;
    private JSONArray jsonArray;

    public JsonHolyWriter(final Writer writer) {
        this.writer = writer;
        jsonArray = new JSONArray();

    }

    /**
     * Write the file in the name of GOD.
     * The final goal is to attain heaven. The path was shown by DIO.
     * I just have to follow my destiny.
     * The answer is in the bone.
     *
     * @param id
     * @param field
     * @param message
     * @throws IOException
     */
    public void holyWriteFile(final int id, final String field,
            final String message) throws IOException {
        final JSONObject obj = writer.writeFile(id, field, message);
        jsonArray.add(obj);
    }

    /**
     * There are 14 phrases that one must keep in mind
     * The Secret Emperor
     * Spiral Staircase
     * Rhinoceros Beetle
     * Pear Tart
     * Rhinoceros Beetle
     * A Ghost Town
     * Giotto
     * Rhinoceros Beetle
     * Singularity Point
     * Hyndragea
     * Singularity Point
     * Angel
     * Via Dolorosa
     * Rhinoceros Beetle
     *
     */
    public void closeJSON() {
        writer.closeJSON(jsonArray);
    }
}
