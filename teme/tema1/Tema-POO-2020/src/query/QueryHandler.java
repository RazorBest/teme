package query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import fileio.ActionInputData;

public abstract class QueryHandler<T> {
    protected T[] array;

    private boolean noPreFilters = false;
    private boolean noPostFilters = false;

    public QueryHandler(final T[] array) {
        this.array = array.clone();
    }

    public QueryHandler(final List<T> list) {
        array = list.toArray(array);
    }

    protected abstract Comparator<T> getComparator(ActionInputData actionData);

    /**
     * Run the respective query. Use this class as a framework.
     * Applying the query method does the following things:
     *      1. Apply the pre-filters to the array (by calling preFilter)
     *      2. Sort the array according to the data in actionData
     *      3. Apply the post-filters  to the array (by calling postFilter)
     *      4. Write the array
     *
     *  The sort rule is determined by the getComparatorMethod (implemented by children)
     *  The setFilters method gives the children the chance to store the
     *      pre-filters and post-filters.
     *
     *  The children have access to one object at at time. (from preFilter and postFilter methods)
     *
     * @param actionData    the data representing the action
     * @return
     */
    public String run(final ActionInputData actionData) {
        final Comparator<T> comparator = getComparator(actionData);
        setFilters(actionData);

        return query(actionData.getNumber(), comparator);
    }

    private String query(final int nm, final Comparator<T> comparator) {
        ArrayList<T> output = new ArrayList<T>(Arrays.asList(array));
        Iterator<T> iterator = output.iterator();

        if (!noPreFilters) {
            while (iterator.hasNext()) {
                final T obj = iterator.next();
                if (!preFilter(obj)) {
                    iterator.remove();
                }
            }
        }

        Collections.sort(output, comparator);
        iterator = output.iterator();

        if (!noPostFilters) {
            while (iterator.hasNext()) {
                final T obj = iterator.next();
                if (!postFilter(obj)) {
                    iterator.remove();
                }
            }
        }

        if (output.size() > nm) {
            output = new ArrayList<T>(output.subList(0, nm));
        }

        return "Query result: " + output.toString();
    }

    /**
     * Give the children the chance to store the filters
     *
     * @param actionData    data about the action
     */
    protected abstract void setFilters(ActionInputData actionData);

    /**
     * Called upon an object from the query object array, before sorting it.
     *
     *
     * @param obj
     * @return  true, to remove the object from the query array
     */
    protected abstract boolean preFilter(T obj);

    /**
     * Called upon an object from the query object array, after sorting it.
     *
     *
     * @param obj
     * @return  true, to remove the object from the query array
     */
    protected abstract boolean postFilter(T obj);

    /**
     * Set the query handler to not call the preFilter method on the query objects.
     *
     * @param noPreFilters
     */
    protected void setNoPreFilters(final boolean noPreFilters) {
        this.noPreFilters = noPreFilters;
    }

    /**
     * Set the query handler to not call the postFilter method on the sorted query objects.
     *
     * @param noPostFilters
     */
    protected void setNoPostFilters(final boolean noPostFilters) {
        this.noPostFilters = noPostFilters;
    }
}
