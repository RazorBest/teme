package strategies;

import java.util.List;

import comparator.MultiComparator;
import comparator.ProducerEnergyComparator;
import comparator.ProducerPriceComparator;
import node.Producer;

public final class PriceProducerStrategy extends ProducerStrategy {
    private final MultiComparator<Producer> comparator;

    public PriceProducerStrategy(final long neededEnergy) {
        super(neededEnergy);

        comparator = new MultiComparator<Producer>();
        comparator.add(new ProducerPriceComparator()).add(new ProducerEnergyComparator());
    }

    @Override
    protected void filterAndSort(final List<Producer> producers) {
        producers.sort(comparator);
    }

}
