package strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import node.Distributor;

public final class DistributorStrategy implements NodeLinkStrategy<Distributor> {

    private List<Distributor> sources;

    @Override
    public void applyStrategy(final Collection<Distributor> candidateSources) {
        long bestPrice = Long.MAX_VALUE;
        Distributor bestDistributor = null;

        for (final Distributor distributor : candidateSources) {
            if (distributor.isOut()) {
                continue;
            }

            final long price = distributor.getPrice();

            if (price != -1 && price < bestPrice) {
                bestPrice = price;
                bestDistributor = distributor;
            }
        }

        sources = new ArrayList<Distributor>();
        sources.add(bestDistributor);
    }

    @Override
    public List<Distributor> getSources() {
        return sources;
    }

}
