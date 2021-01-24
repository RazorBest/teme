package strategies;

import java.util.List;

import comparator.MultiComparator;
import comparator.ProducerEnergyComparator;
import comparator.ProducerGreenComparator;
import comparator.ProducerPriceComparator;
import node.Producer;

public final class GreenProducerStrategy extends ProducerStrategy {
    private final MultiComparator<Producer> comparator;

    public GreenProducerStrategy(final long neededEnergy) {
        super(neededEnergy);

        comparator = new MultiComparator<Producer>();
        comparator.add(new ProducerGreenComparator()).add(new ProducerPriceComparator())
                .add(new ProducerEnergyComparator());
    }

    @Override
    protected void filterAndSort(final List<Producer> producers) {
        producers.sort(comparator);
    }
}
