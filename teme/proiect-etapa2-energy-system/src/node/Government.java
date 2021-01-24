package node;

import dataflow.Request;

public final class Government extends Receiver<Node> {
    private long money = 0;

    protected Government(final long id) {
        super(id);
    }

    @Override
    public void request(final Request request) {
        if (request.getMoney() < 0) {
            money -= request.getMoney();
        }
    }

    @Override
    public void request() {
        for (final Node node : sources) {
            node.request((new Request(this)).setTax(1));
        }
    }

    public long getMoney() {
        return money;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
