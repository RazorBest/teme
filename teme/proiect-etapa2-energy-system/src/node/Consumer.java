package node;

import dataflow.Request;
import strategies.NodeLinkStrategy;

public final class Consumer extends Receiver<Distributor> {
    // private Receiver distributor;

    private long contractPrice;
    private long contract = 0;
    private long money;
    private long postpone;

    protected Consumer(final long id, final long money,
            final NodeLinkStrategy<Distributor> linkStrategy) {
        super(id, linkStrategy);

        this.money = money;
    }

    @Override
    public boolean canChoose() {
        // Remember to check this
        return contract <= 0 || this.sources.size() == 0 || this.sources.get(0).isOut();
    }

    /**
     * Called by the choose method
     */
    @Override
    public void afterChoose() {
        final Distributor distributor = this.sources.get(0);

        contract = distributor.getContractLength();
        contractPrice = distributor.getPrice();
    }

    /**
     * Simulate a passing of a month.
     *
     */
    @Override
    public void update() {
        if (this.isOut()) {
            return;
        }

        contract--;
    }

    @Override
    public void request(final Request request) {
        if (this.isOut()) {
            final Distributor distributor = this.sources.get(0);
            distributor.request((new Request(this)).setCustomer(-1));
            return;
        }

        if (request.getMoney() < 0) {
            money -= request.getMoney();
            return;
        }

        final Distributor distributor = this.sources.get(0);

        distributor.request((new Request(this)).setCustomer(1));

        if (request.getCustomer() != 0) {
            distributor.request((new Request(this)).setCustomer(1));
            return;
        }

        if (request.getTax() > 0) {
            final long price = contractPrice + Math.round(Math.floor(1.2 * postpone));
            if (price <= money) {
                money -= contractPrice;
                distributor.request((new Request(this)).setMoney(-price));
                postpone = 0;
            } else if (postpone == 0) {
                postpone = price;
            } else {
                this.setOut(true);
            }
            return;
        }

        if (request.getEnergy() > 0) {
            distributor.request(request.setSource(this));
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
        return this.sources.get(0);
    }

    public long getContractPrice() {
        return contractPrice;
    }

    public long getContractLength() {
        return contract;
    }
}
