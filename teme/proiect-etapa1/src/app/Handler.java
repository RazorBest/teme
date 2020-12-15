package app;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dataflow.MonthlyUpdate;
import dataflow.Request;
import io.EconomyInputReader;
import io.EconomyWriter;
import node.Consumer;
import node.Distributor;
import node.MoneySource;
import node.Node;
import node.NodeFactory;

public class Handler {
    private final EconomyWriter writer;

    private final List<Node> moneySources;
    private final Map<Long, Node> distributorsMap;
    private final Map<Long, Node> consumersMap;
    private final List<MonthlyUpdate> monthlyUpdates;
    private final Node government;

    public Handler(final EconomyInputReader reader, final EconomyWriter writer) {
        moneySources = reader.getMoneySources();
        distributorsMap = reader.getDistributorsMap();
        consumersMap = reader.getConsumersMap();
        monthlyUpdates = reader.getMonthlyUpdates();
        government = reader.getGovernment();

        this.writer = writer;
    }

    private void update() {
        // Is this too inefficient?
        for (final Node moneySource : moneySources) {
            moneySource.request();
        }
        for (final Node consumer : consumersMap.values()) {
            consumer.update(distributorsMap.values());
        }
        for (final Node distributor : distributorsMap.values()) {
            distributor.update();
        }
        for (final Node consumer : consumersMap.values()) {
            consumer.request(new Request(null).setEnergy(1));
            System.out.println(consumer.getId() + " " + ((Consumer) consumer).getMoney());
        }
        government.request();
    }

    public void runSimulation() {
        update();
        for (final MonthlyUpdate monthlyUpdate : monthlyUpdates) {
            System.out.println("-------------------------------------------------");
            monthlyUpdate.update(this);
            update();

            /*
             * for (final Node distributor : distributorsMap.values()) {
             * System.out.println(distributor); }
             */
        }

        // final int count = 0;
        /*
         * for (final Node consumer : consumersMap.values()) { //
         * System.out.println("consumer " + count); // count++; //
         * consumer.update(distributorsMap.values()); consumer.request((new
         * Request(null)).setCustomer(1)); }
         */

        writer.writeConsumers(consumersMap);
        writer.writeDistributors(distributorsMap);
    }

    public void addConsumer(final long id, final long budget, final long income) {
        final Consumer consumer = (Consumer) NodeFactory.createConsumer(id, budget);
        final MoneySource moneySource = (MoneySource) NodeFactory.createMoneySource(0, income);
        final List<Node> tmpList = new LinkedList<Node>();

        tmpList.add(consumer);
        moneySource.update(tmpList);
        consumersMap.put(id, consumer);
        moneySources.add(moneySource);
    }

    public void updateDistributor(final long id, final long infrastructureCost,
            final long productionCost) {
        final Distributor distributor = (Distributor) distributorsMap.get(id);

        distributor.setInfrastructureCost(infrastructureCost);
        distributor.setProductionCost(productionCost);
    }

    public Map<Long, Node> getConsumersMap() {
        return consumersMap;
    }

    public Map<Long, Node> getDistributorsMap() {
        return distributorsMap;
    }
}
