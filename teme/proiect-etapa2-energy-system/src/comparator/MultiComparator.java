package comparator;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class MultiComparator<T> implements Comparator<T> {
    private final List<Comparator<T>> comparatorList;

    public MultiComparator() {
        comparatorList = new LinkedList<Comparator<T>>();
    }

    /**
     * Adds a comparing rule. The rules can only be added. They cannot be removed.
     *
     * @param comparator
     * @return
     */
    public MultiComparator<T> add(final Comparator<T> comparator) {
        comparatorList.add(comparator);

        return this;
    }

    @Override
    public int compare(final T o1, final T o2) {
        int output = 0;

        for (final Comparator<T> comparator : comparatorList) {
            output = comparator.compare(o1, o2);
            if (output != 0) {
                break;
            }
        }

        return output;
    }

}
