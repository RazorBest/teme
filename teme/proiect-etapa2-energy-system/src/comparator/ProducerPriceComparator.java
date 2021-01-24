package comparator;

import java.util.Comparator;

import node.Producer;

public final class ProducerPriceComparator implements Comparator<Producer> {

    @Override
    public int compare(final Producer o1, final Producer o2) {
        return Double.compare(o1.getPrice(), o2.getPrice());
    }

}
