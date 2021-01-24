package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dataflow.Stats;
import node.Consumer;
import node.Distributor;
import node.Node;
import node.Producer;

public final class EconomyWriter {
    private final String path;
    private final JSONArray jsonConsumers;
    private final JSONArray jsonDistributors;
    private final JSONArray jsonProducers;

    private final Map<Long, Stats> statsMap;

    private long months;

    public EconomyWriter(final String path) {
        this.path = path;
        jsonConsumers = new JSONArray();
        jsonDistributors = new JSONArray();
        jsonProducers = new JSONArray();

        statsMap = new HashMap<Long, Stats>();
    }

    public void setMonths(final long months) {
        this.months = months;
    }

    /**
     * Converts from the consumer database to JSON format. After calling flush, it
     * writes the JSON data on the path.
     *
     * @param consumersMap
     */
    public void writeConsumers(final Map<Long, Consumer> consumersMap) {
        for (final Map.Entry<Long, Consumer> entry : consumersMap.entrySet()) {
            final JSONObject consumerData = new JSONObject();
            final Consumer consumer = entry.getValue();

            consumerData.put("id", entry.getKey());
            consumerData.put("isBankrupt", consumer.isOut());
            consumerData.put("budget", consumer.getMoney());

            jsonConsumers.add(consumerData);
        }
    }

    /**
     * Converts from the distributor database to JSON format. After calling flush,
     * it writes the JSON data on the path.
     *
     * @param distributorsMap
     */
    public void writeDistributors(final Map<Long, Distributor> distributorsMap) {
        for (final Map.Entry<Long, Distributor> entry : distributorsMap.entrySet()) {
            final JSONObject distributorData = new JSONObject();
            final Distributor distributor = entry.getValue();
            final var monthlyStats = distributor.getMonthlyStats();

            // very hacky please don't look
            for (long month = 1; month <= months; month++) {
                long mmonth = month;

                while (!monthlyStats.containsKey(mmonth)) {
                    mmonth--;
                }

                for (final long id : monthlyStats.get(mmonth)) {
                    statsMap.putIfAbsent(id, new Stats(months));
                    statsMap.get(id).add(month, distributor.getId());
                }
            }

            distributorData.put("id", entry.getKey());
            distributorData.put("energyNeededKW", distributor.getEnergyNeeded());
            distributorData.put("contractCost", distributor.getPrice());
            distributorData.put("budget", distributor.getMoney());
            distributorData.put("producerStrategy", distributor.getProducerStrategy());
            distributorData.put("isBankrupt", distributor.isOut());

            final JSONArray consumerArray = new JSONArray();
            for (final Node node : distributor.getConsumers()) {
                final Consumer consumer = (Consumer) node;
                final JSONObject consumerData = new JSONObject();
                consumerData.put("consumerId", consumer.getId());
                consumerData.put("price", consumer.getContractPrice());
                consumerData.put("remainedContractMonths", consumer.getContractLength() - 1);

                consumerArray.add(consumerData);
            }

            distributorData.put("contracts", consumerArray);

            jsonDistributors.add(distributorData);
        }
    }

    /**
     * Converts from the producer database to JSON format. After calling flush, it
     * writes the JSON data on the path.
     *
     * @param producersMap
     */
    public void writeProducers(final Map<Long, Producer> producersMap) {
        for (final Map.Entry<Long, Producer> entry : producersMap.entrySet()) {
            final JSONObject producerData = new JSONObject();
            final Producer producer = entry.getValue();

            producerData.put("id", entry.getKey());
            producerData.put("maxDistributors", producer.getMaxDistributors());
            producerData.put("priceKW", producer.getPrice());
            producerData.put("energyType", producer.getEnergyType());
            producerData.put("energyPerDistributor", producer.getEnergy());

            final JSONArray monthlyStats = new JSONArray();

            jsonProducers.add(producerData);
        }
    }

    /**
     * Write at path the JSON data accumulated through the calls of writeConsumers()
     * and writeDistributors()
     */
    public void flush() {
        final JSONObject jsonObject = new JSONObject();

        for (final Object obj : jsonProducers) {
            final JSONObject producerData = (JSONObject) obj;
            final long id = (long) producerData.get("id");

            final JSONArray monthlyStats;
            if (statsMap.containsKey(id)) {
                monthlyStats = statsMap.get(id).toJSONArray();
            } else {
                monthlyStats = new Stats(months).toJSONArray();
            }

            producerData.put("monthlyStats", monthlyStats);
        }

        jsonObject.put("consumers", jsonConsumers);
        jsonObject.put("distributors", jsonDistributors);
        jsonObject.put("energyProducers", jsonProducers);

        FileWriter file;
        try {
            file = new FileWriter(path);

            file.write(jsonObject.toJSONString());
            file.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
