package node;

import java.util.HashMap;
import java.util.Map;

import dataflow.Request;

public final class Producer extends ObservableNode {
    private final String energyType;
    private final long maxDistributors;
    private final double price;
    private long energy;

    private final Map<Long, Node> customers;

    public Producer(final long id, final String energyType, final long maxDistributors,
            final double price, final long energy) {
        super(id);

        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.price = price;
        this.energy = energy;

        customers = new HashMap<Long, Node>();
    }

    @Override
    public void request(final Request request) {
        final Node source = request.getSource();
        final long id = source.getId();

        if (request.getCustomer() > 0) {
            if (!customers.containsKey(id)) {
                customers.put(id, source);
            }
        } else if (request.getCustomer() < 0) {
            customers.remove(id);
        }
    }

    /**
     * Called to notify the observers
     */
    public void changeState() {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     *
     * @return the number of customers that can be submitted to this producer
     */
    public long getRemainingCustomers() {
        return maxDistributors - customers.size();
    }

    public String getEnergyType() {
        return energyType;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Set the energy and call the ChangeState method for the observers.
     *
     * @param energy
     */
    public void setEnergy(final long energy) {
        this.energy = energy;
        changeState();
    }

    public long getEnergy() {
        return energy;
    }

    public double getProductionCost() {
        return price * energy;
    }

    public long getMaxDistributors() {
        return maxDistributors;
    }
}
