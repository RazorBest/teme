package dataflow;

import node.Node;

public final class Request {
    private Node source;

    private long tax = 0;
    private long link = 0;
    private long money = 0;
    private long customer = 0;
    private long energy = 0;

    public Request(final Node source) {
        this.source = source;
    }

    public Request setSource(final Node source) {
        this.source = source;
        return this;
    }

    public Request setMoney(final long money) {
        this.money = money;
        return this;
    }

    public Request setCustomer(final long customer) {
        this.customer = customer;
        return this;
    }

    public Request setEnergy(final long energy) {
        this.energy = energy;
        return this;
    }

    public Request setLink(final long link) {
        this.link = link;
        return this;
    }

    public Request setTax(final long tax) {
        this.tax = tax;
        return this;
    }

    public void reflect(final Node newSource) {
        final Node oldSource = source;
        source = newSource;

        oldSource.request(this);
    }

    public void reflect(final Node newSource, final Request request) {
        final Node oldSource = source;
        source = newSource;

        oldSource.request(request);
    }

    public Node getSource() {
        return source;
    }

    public long getCustomer() {
        return customer;
    }

    public long getEnergy() {
        return energy;
    }

    public long getMoney() {
        return money;
    }

    public long getLink() {
        return link;
    }

    public long getTax() {
        return tax;
    }

}
