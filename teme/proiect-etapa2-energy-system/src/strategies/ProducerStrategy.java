package strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import node.Producer;

public abstract class ProducerStrategy implements NodeLinkStrategy<Producer> {
    private List<Producer> sources;

    protected long neededEnergy = 0;

    public ProducerStrategy(final long neededEnergy) {
        this.neededEnergy = neededEnergy;
    }

    protected abstract void filterAndSort(List<Producer> producers);

    @Override
    public final void applyStrategy(final Collection<Producer> candidateSources) {
        sources = new ArrayList<Producer>(candidateSources);
        filterAndSort(sources);

        sources.removeIf(producer -> producer.getRemainingCustomers() <= 0);

        int lastIndex = 0;
        long accumulatedEnergy = 0;

        for (final Producer producer : sources) {
            accumulatedEnergy += producer.getEnergy();
            lastIndex++;

            if (accumulatedEnergy >= this.neededEnergy) {
                break;
            }
        }

        sources.subList(lastIndex, sources.size()).clear();
    }

    @Override
    public final List<Producer> getSources() {
        return sources;
    }
}
