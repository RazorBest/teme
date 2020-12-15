package node;

import dataflow.Request;

public final class Consumer extends Node {
    private Node distributor;

    private long contractPrice;
    private long contract;
    private long money;
    private long postpone;

    protected Consumer(final long id, final long money) {
        super(id);

        this.money = money;
    }

    @Override
    public void update(final Iterable<Node> nodes) {
        contract--;
        // remember to check this
        if (contract > 0 && distributor != null && !distributor.isOut()) {
            return;
        }

        distributor = null;
        postpone = 0;

        if (this.isOut()) {
            return;
        }

        Distributor bestDistributor = null;
        long bestPrice = Long.MAX_VALUE;

        for (final Node node : nodes) {
            if (!Distributor.class.isInstance(node) || node.isOut()) {
                continue;
            }

            final Distributor distributor = (Distributor) node;

            final long price = distributor.getPrice();

            if (price != -1 && price < bestPrice) {
                bestPrice = price;
                bestDistributor = (Distributor) node;
            }
        }

        distributor = bestDistributor;
        contract = bestDistributor.getContractLength();
        contractPrice = bestPrice;

        // System.out.println("choose " + bestPrice);
    }

    @Override
    public void request(final Request request) {
        if (this.isOut()) {
            return;
        }

        if (request.getMoney() < 0) {
            money -= request.getMoney();
            return;
        }

        if (request.getEnergy() > 0) {
            final long price = contractPrice + Math.round(Math.floor(1.2 * postpone));
            if (price <= money) {
                money -= contractPrice;
                distributor.request(request.setSource(this).setMoney(-price).setCustomer(1));
                // System.out.println("pay " + price);
                postpone = 0;
            } else if (postpone == 0) {
                postpone = price;
                distributor.request(request.setSource(this).setCustomer(1));
            } else {
                distributor.request(request.setSource(this).setCustomer(1));
                this.setOut(true);
            }
            return;
        }

        if (request.getCustomer() > 0) {
            distributor.request(request.setSource(this));
        }
    }

    public long getMoney() {
        return money;
    }

    public Distributor getDistributor() {
        return (Distributor) distributor;
    }

    public long getContractPrice() {
        return contractPrice;
    }

    public long getContractLength() {
        return contract;
    }

    @Override
    public String toString() {
        return "budget: " + money + ", isBankrupt: " + this.isOut();
    }
}
