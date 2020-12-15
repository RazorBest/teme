package dataflow;

import java.util.List;

import app.Handler;

public final class MonthlyUpdate {
    private final List<Update> consumerUpdates;
    private final List<Update> distributorUpdates;

    public MonthlyUpdate(final List<Update> consumerUpdates,
            final List<Update> distributorUpdates) {
        this.consumerUpdates = consumerUpdates;
        this.distributorUpdates = distributorUpdates;
    }

    public void update(final Handler handler) {
        for (final Update update : consumerUpdates) {
            handler.addConsumer(update.getId(), update.getBudget(), update.getIncome());
        }
        for (final Update update : distributorUpdates) {
            handler.updateDistributor(update.getId(), update.getInfrastructureCost(),
                    update.getProductionCost());
        }
    }
}
