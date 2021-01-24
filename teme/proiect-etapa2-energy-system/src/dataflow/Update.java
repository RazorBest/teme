package dataflow;

public final class Update {
    private long id = 0;
    private long budget = 0;
    private long income = 0;
    private long infrastructureCost = 0;
    private long energyPerDistributor = 0;

    /**
     * Builder setter
     *
     * @param id
     * @return itself
     */
    public Update setId(final long newId) {
        this.id = newId;
        return this;
    }

    /**
     * Builder setter
     *
     * @param budget
     * @return itself
     */
    public Update setBudget(final long newBudget) {
        this.budget = newBudget;
        return this;
    }

    /**
     * Builder setter
     *
     * @param income
     * @return itself
     */
    public Update setIncome(final long newIncome) {
        this.income = newIncome;
        return this;
    }

    /**
     * Builder setter
     *
     * @param infrastructureCost
     * @return itself
     */
    public Update setInfrastructureCost(final long newInfrastructureCost) {
        this.infrastructureCost = newInfrastructureCost;
        return this;
    }

    /**
     * Builder setter
     *
     * @param newEnergyPerDistributor
     * @return itself
     */
    public Update setEnergyPerDistributor(final long newEnergyPerDistributor) {
        this.energyPerDistributor = newEnergyPerDistributor;
        return this;
    }

    public long getId() {
        return id;
    }

    public long getBudget() {
        return budget;
    }

    public long getIncome() {
        return income;
    }

    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

}
