package dataflow;

import java.util.List;

import app.Handler;

/**
 * Container class that stores the updates of the nodes.
 *
 * @author Prciop Marius Razvan
 *
 */
public final class MonthlyUpdate {
    private final List<Update> consumerUpdates;
    private final List<Update> distributorUpdates;
    private final List<Update> producerUpdates;

    public MonthlyUpdate(final List<Update> consumerUpdates, final List<Update> distributorUpdates,
            final List<Update> producerUpdates) {
        this.consumerUpdates = consumerUpdates;
        this.distributorUpdates = distributorUpdates;
        this.producerUpdates = producerUpdates;
    }

    /**
     * Apply the consumer updates stored in this object to the app handler.
     *
     * @param handler
     */
    public void updateConsumers(final Handler handler) {
        for (final Update update : consumerUpdates) {
            handler.addConsumer(update.getId(), update.getBudget(), update.getIncome());
        }
    }

    /**
     * Apply the distributor updates stored in this object to the app handler.
     *
     * @param handler
     */
    public void updateDistributors(final Handler handler) {
        for (final Update update : distributorUpdates) {
            handler.updateDistributor(update.getId(), update.getInfrastructureCost());
        }
    }

    /**
     * Apply the producer updates stored in this object to the app handler.
     *
     * @param handler
     */
    public void updateProducers(final Handler handler) {
        for (final Update update : producerUpdates) {
            handler.updateProducer(update.getId(), update.getEnergyPerDistributor());
        }
    }

    /**
     * Apply the updates stored in this object to the app handler.
     *
     * @param handler
     */
    public void updateAll(final Handler handler) {
        updateConsumers(handler);
        updateDistributors(handler);
        updateProducers(handler);
    }
}
