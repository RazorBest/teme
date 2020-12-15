package io;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dataflow.MonthlyUpdate;
import dataflow.Update;
import node.Node;
import node.NodeFactory;

public class EconomyInputReader {
    private Node government;
    private List<Node> moneySources;
    private HashMap<Long, Node> consumersMap;
    private HashMap<Long, Node> distributorsMap;
    private List<MonthlyUpdate> monthlyUpdates;
    private long numberOfTurns;

    public void read(final String path) {
        this.moneySources = new LinkedList<Node>();
        this.consumersMap = new HashMap<Long, Node>();
        this.distributorsMap = new HashMap<Long, Node>();
        this.monthlyUpdates = new LinkedList<MonthlyUpdate>();

        final JSONParser parser = new JSONParser();

        try {
            final Object object = parser.parse(new FileReader(path));
            final JSONObject jsonObject = (JSONObject) object;

            this.numberOfTurns = (long) jsonObject.get("numberOfTurns");

            final JSONObject data = (JSONObject) jsonObject.get("initialData");
            final JSONArray consumers = (JSONArray) data.get("consumers");
            final JSONArray distributors = (JSONArray) data.get("distributors");

            for (final Object obj : consumers) {
                final JSONObject consumerObj = (JSONObject) obj;
                final Node consumer = NodeFactory.createConsumer((long) consumerObj.get("id"),
                        (long) consumerObj.get("initialBudget"));

                this.consumersMap.put((long) consumerObj.get("id"), consumer);

                final Node moneySource = NodeFactory.createMoneySource(0,
                        (long) consumerObj.get("monthlyIncome"));

                final LinkedList<Node> moneySourceConsumers = new LinkedList<Node>();
                moneySourceConsumers.add(consumer);
                moneySource.update(moneySourceConsumers);

                this.moneySources.add(moneySource);
            }

            for (final Object obj : distributors) {
                final JSONObject distributorObj = (JSONObject) obj;
                final Node distributor = NodeFactory.createDistributor(
                        (long) distributorObj.get("id"),
                        (long) distributorObj.get("contractLength"),
                        (long) distributorObj.get("initialBudget"),
                        (long) distributorObj.get("initialInfrastructureCost"),
                        (long) distributorObj.get("initialProductionCost"));

                this.distributorsMap.put((long) distributorObj.get("id"), distributor);
            }

            this.government = NodeFactory.createGovernment(0);
            this.government.update(distributorsMap.values());

            final JSONArray monthlyUpdatesObj = (JSONArray) jsonObject.get("monthlyUpdates");

            for (final Object updateObj : monthlyUpdatesObj) {
                final JSONObject update = (JSONObject) updateObj;
                final JSONArray newConsumers = (JSONArray) update.get("newConsumers");
                final List<Update> consumerUpdates = new LinkedList<Update>();

                for (final Object consumerUpdateObj : newConsumers) {
                    final JSONObject consumerUpdate = (JSONObject) consumerUpdateObj;
                    final long id = (long) consumerUpdate.get("id");
                    final long budget = (long) consumerUpdate.get("initialBudget");
                    final long income = (long) consumerUpdate.get("monthlyIncome");

                    consumerUpdates
                            .add((new Update()).setId(id).setBudget(budget).setIncome(income));
                }

                final JSONArray costChanges = (JSONArray) update.get("costsChanges");
                final List<Update> distributorUpdates = new LinkedList<Update>();

                for (final Object costChangeObj : costChanges) {
                    final JSONObject costChange = (JSONObject) costChangeObj;
                    final long id = (long) costChange.get("id");
                    final long infrastructureCost = (long) costChange.get("infrastructureCost");
                    final long productionCost = (long) costChange.get("productionCost");

                    distributorUpdates
                            .add((new Update()).setId(id).setInfrastructureCost(infrastructureCost)
                                    .setProductionCost(productionCost));
                }

                final MonthlyUpdate monthlyUpdate = new MonthlyUpdate(consumerUpdates,
                        distributorUpdates);
                this.monthlyUpdates.add(monthlyUpdate);
            }

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public Node getGovernment() {
        return government;
    }

    public List<Node> getMoneySources() {
        return moneySources;
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    public HashMap<Long, Node> getConsumersMap() {
        return consumersMap;
    }

    public HashMap<Long, Node> getDistributorsMap() {
        return distributorsMap;
    }
}
