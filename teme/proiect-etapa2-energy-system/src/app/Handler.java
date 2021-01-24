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
import node.Producer;
import node.Receiver;

public final class Handler {
    private static Handler handler = null;

    private EconomyWriter writer;

    private List<Receiver<Node>> moneySources;
    private Map<Long, Producer> producersMap;
    private Map<Long, Distributor> distributorsMap;
    private Map<Long, Consumer> consumersMap;
    private List<MonthlyUpdate> monthlyUpdates;
    private Receiver<Node> government;

    private Handler() {
    }

    /**
     *
     * @return a singleton instance of this class.
     */
    public static Handler getInstance() {

        if (handler == null) {
            handler = new Handler();
        }
        return handler;

    }

    /**
     * Populate the handler's database using the reader
     *
     * @param reader
     */
    public void setReader(final EconomyInputReader reader) {
        moneySources = reader.getMoneySources();
        distributorsMap = reader.getDistributorsMap();
        consumersMap = reader.getConsumersMap();
        producersMap = reader.getProducersMap();
        monthlyUpdates = reader.getMonthlyUpdates();
        government = reader.getGovernment();
    }

    private void initializeNodes() {

    }

    public void setWriter(final EconomyWriter writer) {
        this.writer = writer;
    }

    /**
     * Updates the state of the nodes. It simulates the passing of a month.
     *
     */
    private void update() {
        for (final Receiver<Node> moneySource : moneySources) {
            moneySource.request();
        }

        for (final Node node : distributorsMap.values()) {
            node.update();
        }
        for (final Node node : consumersMap.values()) {
            node.update();
        }

        for (final Receiver<Producer> receiver : distributorsMap.values()) {
            receiver.choose(producersMap.values());
        }

        for (final Receiver<Distributor> receiver : consumersMap.values()) {
            receiver.choose(distributorsMap.values());
        }
        for (final Node node : consumersMap.values()) {
            node.request((new Request(null)).setCustomer(1));
            node.request(new Request(null).setEnergy(1));
        }

        government.request();

        for (final Node node : consumersMap.values()) {
            node.request((new Request(null)).setCustomer(-1));
        }
    }

    /**
     * Start the simulation. It runs the update method and apply the monthlyUpdates
     * for every month by changing the states of the nodes.
     *
     * At the end, it writes the state of the consumer and distributor nodes using
     * the writer object.
     */
    public void runSimulation() {

        for (final Receiver<Producer> receiver : distributorsMap.values()) {
            receiver.choose(producersMap.values());
        }

        for (final Receiver<Distributor> receiver : consumersMap.values()) {
            receiver.choose(distributorsMap.values());
        }

        for (final Node moneySource : moneySources) {
            moneySource.request();
        }

        for (final Receiver<Distributor> receiver : consumersMap.values()) {
            receiver.request((new Request(null)).setEnergy(1));
        }

        government.request();

        for (final MonthlyUpdate monthlyUpdate : monthlyUpdates) {
            monthlyUpdate.updateAll(this);
            update();
        }

        writer.setMonths(monthlyUpdates.size());
        writer.writeConsumers(consumersMap);
        writer.writeDistributors(distributorsMap);
        writer.writeProducers(producersMap);
    }

    /**
     * Changes the state of the consumer database. Called by MonthlyUpdate methods.
     *
     * @param id     = the id of the new consumer
     * @param budget
     * @param income
     */
    public void addConsumer(final long id, final long budget, final long income) {
        final Consumer consumer = NodeFactory.createConsumer(id, budget);
        final MoneySource moneySource = NodeFactory.createMoneySource(0, income);
        final List<Node> tmpList = new LinkedList<Node>();

        tmpList.add(consumer);
        moneySource.choose(tmpList);
        consumersMap.put(id, consumer);
        moneySources.add(moneySource);
    }

    /**
     * Changes the state of a distributor. Called by MonthlyUpdate methods.
     *
     * @param id                 = the id of the distributor
     * @param infrastructureCost
     * @param productionCost
     */
    public void updateDistributor(final long id, final long infrastructureCost) {
        final Distributor distributor = distributorsMap.get(id);

        distributor.setInfrastructureCost(infrastructureCost);
    }

    /**
     * Changes the state of a producer. Called by MonthlyUpdate methods.
     *
     * @param id
     * @param energy
     */
    public void updateProducer(final long id, final long energy) {
        final Producer producer = producersMap.get(id);

        producer.setEnergy(energy);
    }

    public Map<Long, Consumer> getConsumersMap() {
        return consumersMap;
    }

    public Map<Long, Distributor> getDistributorsMap() {
        return distributorsMap;
    }
}
