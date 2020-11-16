package app;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import fileio.JsonHolyWriter;
import fileio.Writer;

public abstract class QueryHandler {
	private Object[] array;
	private Comparator<Object> comparator;
	private JsonHolyWriter writer;
	
	public QueryHandler(Object[] array, Comparator<Object> comparator, JsonHolyWriter writer) {
		this.array = array.clone();
		this.comparator = comparator;
		this.writer = writer;
	}
	
	public QueryHandler(List<Object> list, Comparator<Object> comparator) {
		array = list.toArray();
	}
	
	public void query(int n) {
		Arrays.sort(array, comparator);
		List<Object> output = Arrays.asList(Arrays.copyOfRange(array, 0, n - 1));
		Iterator<Object> iterator = output.iterator();
		
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (!filter(obj)) {
				iterator.remove();
			}
		}
	}
	
	public abstract boolean filter(Object obj);
}
