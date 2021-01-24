package comparator;

import java.util.Comparator;

import node.Producer;

public final class ProducerEnergyComparator implements Comparator<Producer> {

    @Override
    public int compare(final Producer o1, final Producer o2) {
        return Long.compare(o2.getEnergy(), o1.getEnergy());
    }

}
