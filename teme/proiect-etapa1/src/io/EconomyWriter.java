package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import node.Consumer;
import node.Distributor;
import node.Node;

public class EconomyWriter {
    private final String path;
    private final JSONArray jsonConsumers;
    private final JSONArray jsonDistributors;

    public EconomyWriter(final String path) {
        this.path = path;
        jsonConsumers = new JSONArray();
        jsonDistributors = new JSONArray();
    }

    public void writeConsumers(final Map<Long, Node> consumersMap) {
        for (final Map.Entry<Long, Node> entry : consumersMap.entrySet()) {
            final JSONObject consumerData = new JSONObject();
            final Consumer consumer = (Consumer) entry.getValue();

            consumerData.put("id", entry.getKey());
            consumerData.put("isBankrupt", consumer.isOut());
            consumerData.put("budget", consumer.getMoney());

            jsonConsumers.add(consumerData);
        }
    }

    public void writeDistributors(final Map<Long, Node> distributorsMap) {
        for (final Map.Entry<Long, Node> entry : distributorsMap.entrySet()) {
            final JSONObject distributorData = new JSONObject();
            final Distributor distributor = (Distributor) entry.getValue();

            distributorData.put("id", entry.getKey());
            distributorData.put("isBankrupt", distributor.isOut());
            distributorData.put("budget", distributor.getMoney());

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

    public void flush() {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("consumers", jsonConsumers);
        jsonObject.put("distributors", jsonDistributors);

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
