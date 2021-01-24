package strategies;

import java.util.Comparator;
import java.util.List;

import comparator.ProducerEnergyComparator;
import node.Producer;

public final class QuantityProducerStrategy extends ProducerStrategy {
    private final Comparator<Producer> comparator;

    public QuantityProducerStrategy(final long neededEnergy) {
        super(neededEnergy);

        comparator = new ProducerEnergyComparator();
    }

    @Override
    protected void filterAndSort(final List<Producer> producers) {
        producers.sort(comparator);
    }

}
