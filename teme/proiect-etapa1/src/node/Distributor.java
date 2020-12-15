package node;

import java.util.HashSet;
import java.util.Set;

import dataflow.Request;

public final class Distributor extends Node {
    private final long contractLength;
    private long infrastructureCost;
    private long productionCost;

    private long money;
    private Set<Node> consumers = new HashSet<Node>();;

    protected Distributor(final long id, final long contractLength, final long budget,
            final long infrastructureCost, final long productionCost) {
        super(id);

        this.contractLength = contractLength;
        this.money = budget;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    @Override
    public void update() {
        consumers = new HashSet<Node>();
    }

    @Override
    public void request(final Request request) {
        if (this.isOut()) {
            return;
        }

        if (request.getTax() > 0) {
            final long tax = infrastructureCost + productionCost * consumers.size();
            if (tax <= money) {
                money -= tax;
                request.reflect(this, (new Request(this)).setMoney(-tax));
            } else {
                this.setOut(true);
            }

            return;
        }

        if (request.getMoney() < 0) {
            money -= request.getMoney();
        }

        if (request.getCustomer() > 0) {
            if (!consumers.contains(request.getSource())) {
                consumers.add(request.getSource());
            }
        }
    }

    private long getProfit() {
        return Math.round(Math.floor(0.2 * productionCost));
    }

    public long getPrice() {
        final long price = productionCost + getProfit();

        if (consumers.size() > 0) {
            return Math.round(price + Math.floor(infrastructureCost / consumers.size()));
        }
        return price + infrastructureCost;
    }

    public long getContractLength() {
        return contractLength;
    }

    public Set<Node> getConsumers() {
        return consumers;
    }

    public long getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "distributor budget: " + money;
    }

    public void setInfrastructureCost(final long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public void setProductionCost(final long productionCost) {
        this.productionCost = productionCost;
    }

}
