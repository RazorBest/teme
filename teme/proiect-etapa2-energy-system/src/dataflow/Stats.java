package dataflow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class Stats {
    private final Map<Long, List<Long>> data;

    public Stats(final long months) {

        data = new HashMap<Long, List<Long>>();
        for (long i = 1; i <= months; i++) {
            data.put(i, new LinkedList<Long>());
        }
    }

    /**
     * Add a stat that represents a pair (month, id)
     *
     * @param month
     * @param id
     */
    public void add(final long month, final long id) {
        data.putIfAbsent(month, new LinkedList<Long>());
        data.get(month).add(id);
    }

    /**
     * Convert the accumulated stats (from the add method) to a JSONArray.
     *
     * @return
     */
    public JSONArray toJSONArray() {
        final JSONArray arrayObj = new JSONArray();

        for (final var entry : data.entrySet()) {
            final long month = entry.getKey();
            final JSONObject monthDataObj = new JSONObject();
            final JSONArray distributorsIdArrayObj = new JSONArray();

            monthDataObj.put("month", month);

            for (final long id : entry.getValue()) {
                distributorsIdArrayObj.add(id);
            }

            monthDataObj.put("distributorsIds", distributorsIdArrayObj);
            arrayObj.add(monthDataObj);
        }

        return arrayObj;
    }
}
