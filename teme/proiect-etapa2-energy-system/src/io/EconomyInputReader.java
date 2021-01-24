package io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dataflow.MonthlyUpdate;
import dataflow.Update;
import node.Consumer;
import node.Distributor;
import node.Node;
import node.NodeFactory;
import node.Producer;
import node.Receiver;

public final class EconomyInputReader {
    private Receiver<Node> government;
    private List<Receiver<Node>> moneySources;
    private HashMap<Long, Receiver<Distributor>> consumersMap;
    private HashMap<Long, Receiver<Producer>> distributorsMap;
    private HashMap<Long, Node> producersMap;
    private List<MonthlyUpdate> monthlyUpdates;
    private long numberOfTurns;

    /**
     * This method can be split in smaller modules
     *
     * @param path
     */
    public void read(final String path) {
        this.moneySources = new LinkedList<Receiver<Node>>();
        this.consumersMap = new LinkedHashMap<Long, Receiver<Distributor>>();
        this.distributorsMap = new LinkedHashMap<Long, Receiver<Producer>>();
        this.producersMap = new LinkedHashMap<Long, Node>();
        this.monthlyUpdates = new LinkedList<MonthlyUpdate>();

        final JSONParser parser = new JSONParser();

        try {
            final Object object = parser.parse(new FileReader(path));
            final JSONObject jsonObject = (JSONObject) object;

            this.numberOfTurns = (long) jsonObject.get("numberOfTurns");

            final JSONObject data = (JSONObject) jsonObject.get("initialData");
            final JSONArray consumers = (JSONArray) data.get("consumers");
            final JSONArray distributors = (JSONArray) data.get("distributors");
            final JSONArray producers = (JSONArray) data.get("producers");

            for (final Object obj : consumers) {
                final JSONObject consumerObj = (JSONObject) obj;
                final Receiver<Distributor> consumer = NodeFactory.createConsumer(
                        (long) consumerObj.get("id"), (long) consumerObj.get("initialBudget"));

                this.consumersMap.put((long) consumerObj.get("id"), consumer);

                final Receiver<Node> moneySource = NodeFactory.createMoneySource(0,
                        (long) consumerObj.get("monthlyIncome"));

                final LinkedList<Node> moneySourceConsumers = new LinkedList<Node>();
                moneySourceConsumers.add(consumer);
                moneySource.choose(moneySourceConsumers);

                this.moneySources.add(moneySource);
            }

            for (final Object obj : distributors) {
                final JSONObject distributorObj = (JSONObject) obj;
                final Receiver<Producer> distributor = NodeFactory.createDistributor(
                        (long) distributorObj.get("id"),
                        (long) distributorObj.get("contractLength"),
                        (long) distributorObj.get("initialBudget"),
                        (long) distributorObj.get("initialInfrastructureCost"),
                        (long) distributorObj.get("energyNeededKW"),
                        (String) distributorObj.get("producerStrategy"));

                this.distributorsMap.put((long) distributorObj.get("id"), distributor);
            }

            for (final Object obj : producers) {
                final JSONObject producerObj = (JSONObject) obj;
                final Node producer = NodeFactory.createProducer((long) producerObj.get("id"),
                        (String) producerObj.get("energyType"),
                        (long) producerObj.get("maxDistributors"),
                        (double) producerObj.get("priceKW"),
                        (long) producerObj.get("energyPerDistributor"));

                this.producersMap.put((long) producerObj.get("id"), producer);
            }

            this.government = NodeFactory.createGovernment(0);
            this.government.choose(Collections.unmodifiableCollection(distributorsMap.values()));

            final JSONArray monthlyUpdatesObj = (JSONArray) jsonObject.get("monthlyUpdates");

            readMonthlyUpdates(monthlyUpdatesObj);
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void readMonthlyUpdates(final JSONArray monthlyUpdatesObj) {
        for (final Object updateObj : monthlyUpdatesObj) {
            final JSONObject update = (JSONObject) updateObj;

            final JSONArray newConsumers = (JSONArray) update.get("newConsumers");
            final List<Update> consumerUpdates = new LinkedList<Update>();
            for (final Object consumerUpdateObj : newConsumers) {
                final JSONObject consumerUpdate = (JSONObject) consumerUpdateObj;
                final long id = (long) consumerUpdate.get("id");
                final long budget = (long) consumerUpdate.get("initialBudget");
                final long income = (long) consumerUpdate.get("monthlyIncome");

                consumerUpdates.add((new Update()).setId(id).setBudget(budget).setIncome(income));
            }

            final JSONArray distributorChanges = (JSONArray) update.get("distributorChanges");
            final List<Update> distributorUpdates = new LinkedList<Update>();
            for (final Object changeObj : distributorChanges) {
                final JSONObject change = (JSONObject) changeObj;
                final long id = (long) change.get("id");
                final long infrastructureCost = (long) change.get("infrastructureCost");

                distributorUpdates
                        .add((new Update()).setId(id).setInfrastructureCost(infrastructureCost));
            }

            final JSONArray producerChanges = (JSONArray) update.get("producerChanges");
            final List<Update> producerUpdates = new LinkedList<Update>();
            for (final Object changeObj : producerChanges) {
                final JSONObject change = (JSONObject) changeObj;
                final long id = (long) change.get("id");
                final long energyPerDistributor = (long) change.get("energyPerDistributor");

                producerUpdates.add(
                        (new Update()).setId(id).setEnergyPerDistributor(energyPerDistributor));
            }

            final MonthlyUpdate monthlyUpdate = new MonthlyUpdate(consumerUpdates,
                    distributorUpdates, producerUpdates);
            this.monthlyUpdates.add(monthlyUpdate);
        }
    }

    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public Receiver<Node> getGovernment() {
        return government;
    }

    public List<Receiver<Node>> getMoneySources() {
        return moneySources;
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     *
     * @return the consumers data map
     */
    public HashMap<Long, Consumer> getConsumersMap() {
        final HashMap<Long, Consumer> map = new HashMap<Long, Consumer>();

        for (final Map.Entry<Long, Receiver<Distributor>> entry : consumersMap.entrySet()) {
            map.put(entry.getKey(), (Consumer) entry.getValue());
        }

        return map;
    }

    /**
     *
     * @return the distributors data map
     */
    public HashMap<Long, Distributor> getDistributorsMap() {
        final HashMap<Long, Distributor> map = new HashMap<Long, Distributor>();

        for (final Map.Entry<Long, Receiver<Producer>> entry : distributorsMap.entrySet()) {
            map.put(entry.getKey(), (Distributor) entry.getValue());
        }

        return map;
    }

    /**
     *
     * @return the producers data map
     */
    public HashMap<Long, Producer> getProducersMap() {
        final HashMap<Long, Producer> map = new HashMap<Long, Producer>();

        for (final Map.Entry<Long, Node> entry : producersMap.entrySet()) {
            map.put(entry.getKey(), (Producer) entry.getValue());
        }

        return map;
    }
}
