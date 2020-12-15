package node;

public class NodeFactory {
    public static Node createConsumer(final long id, final long money) {
        return new Consumer(id, money);
    }

    public static Node createMoneySource(final long id, final long money) {
        return new MoneySource(id, money);
    }

    public static Node createGovernment(final long id) {
        return new Government(id);
    }

    public static Node createDistributor(final long id, final long contractLength,
            final long initialBudget, final long initialInfrastructureCost,
            final long initialProductionCost) {
        return new Distributor(id, contractLength, initialBudget, initialInfrastructureCost,
                initialProductionCost);
    }
}
