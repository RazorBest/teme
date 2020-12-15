package dataflow;

public final class Update {
    private long id = 0;
    private long budget = 0;
    private long income = 0;
    private long infrastructureCost = 0;
    private long productionCost = 0;

    public Update setId(final long id) {
        this.id = id;
        return this;
    }

    public Update setBudget(final long budget) {
        this.budget = budget;
        return this;
    }

    public Update setIncome(final long income) {
        this.income = income;
        return this;
    }

    public Update setInfrastructureCost(final long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
        return this;
    }

    public Update setProductionCost(final long productionCost) {
        this.productionCost = productionCost;
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

    public long getProductionCost() {
        return productionCost;
    }

}
