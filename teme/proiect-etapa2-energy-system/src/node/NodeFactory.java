package node;

import strategies.DistributorStrategy;
import strategies.GreenProducerStrategy;
import strategies.NodeLinkStrategy;
import strategies.PriceProducerStrategy;
import strategies.QuantityProducerStrategy;

public final class NodeFactory {
    private static final NodeLinkStrategy<Distributor> DIST_STRATEGY = new DistributorStrategy();

    private NodeFactory() {
    }

    /**
     *
     * @param id    - a unique number
     * @param money - the starting budget of the consumer
     * @return
     */
    public static Consumer createConsumer(final long id, final long money) {
        return new Consumer(id, money, DIST_STRATEGY);
    }

    /**
     *
     * @param id    - a unique number
     * @param money - the money that will be generate at each request of the money
     *              source
     * @return
     */
    public static MoneySource createMoneySource(final long id, final long money) {
        return new MoneySource(id, money);
    }

    /**
     * A node used to receive all the money. Because some people can't stop binge
     * eating.
     *
     * @param id - a unique number
     * @return
     */
    public static Government createGovernment(final long id) {
        return new Government(id);
    }

    /**
     *
     * @param id                        - a unique number
     * @param contractLength            - the length of a contract of a distributor
     * @param initialBudget             - initial budget of the distributor
     * @param initialInfrastructureCost - cost of infrastructure; who knows what
     *                                  they do with this money?
     * @param initialProductionCost     - cost of production of the electric
     *                                  current.
     * @return
     */
    public static Distributor createDistributor(final long id, final long contractLength,
            final long initialBudget, final long initialInfrastructureCost, final long energy,
            final String strategy) {

        final NodeLinkStrategy<Producer> producerStrategy;

        switch (strategy) {
        case "GREEN":
            producerStrategy = new GreenProducerStrategy(energy);
            break;
        case "PRICE":
            producerStrategy = new PriceProducerStrategy(energy);
            break;
        case "QUANTITY":
            producerStrategy = new QuantityProducerStrategy(energy);
            break;
        default:
            producerStrategy = null;
        }

        return new Distributor(id, contractLength, initialBudget, initialInfrastructureCost, energy,
                strategy, producerStrategy);
    }

    /**
     *
     * @param id
     * @param energyType
     * @param maxDistributors
     * @param priceKW
     * @param energyPerDistributor
     * @return a producer object
     */
    public static Producer createProducer(final long id, final String energyType,
            final long maxDistributors, final double priceKW, final long energyPerDistributor) {

        return new Producer(id, energyType, maxDistributors, priceKW, energyPerDistributor);
    }
}
