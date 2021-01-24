package dataflow;

import node.Node;

/**
 *
 * @author Pricop Marius Razvan
 *
 *         Container class used by various instances of Node.
 */
public final class Request {
    private Node source;

    private long tax = 0;
    private long money = 0;
    private long customer = 0;
    private long energy = 0;

    public Request(final Node source) {
        this.source = source;
    }

    /**
     * Builder setter
     *
     * The source that generates the request.
     *
     * @param source
     * @return itself
     */
    public Request setSource(final Node newSource) {
        this.source = newSource;
        return this;
    }

    /**
     * Builder setter
     *
     * Marks a money transaction. If money is positive, then it is expected to send
     * to the source that amount of money. If money is negative, it is considered as
     * received.
     *
     * @param money
     * @return itself
     */
    public Request setMoney(final long newMoney) {
        this.money = newMoney;
        return this;
    }

    /**
     * Builder setter
     *
     * Marks the source to be a customer.
     *
     * @param customer
     * @return itself
     */
    public Request setCustomer(final long newCustomer) {
        this.customer = newCustomer;
        return this;
    }

    /**
     * Builder setter
     *
     * @param energy
     * @return itself
     */
    public Request setEnergy(final long newEnergy) {
        this.energy = newEnergy;
        return this;
    }

    /**
     * Builder setter
     *
     * Marks the request of an unknown sum of money. Lets the receiver make a
     * request to the source with a chosen value of money.
     *
     * @param tax
     * @return itself
     */
    public Request setTax(final long newTax) {
        this.tax = newTax;
        return this;
    }

    /**
     * Send back the request from a new source to this object's source.
     *
     * @param newSource
     */
    public void reflect(final Node newSource) {
        final Node oldSource = source;
        source = newSource;

        oldSource.request(this);
    }

    /**
     * Send a new request from a new source to this object's source.
     *
     * @param newSource
     * @param request
     */
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

    public long getTax() {
        return tax;
    }

}
