package node;

import dataflow.Request;

public final class MoneySource extends Receiver<Node> {
    private final long money;

    protected MoneySource(final long id, final long money) {
        super(id);

        this.money = money;
    }

    @Override
    public void request() {
        for (final Node node : sources) {
            node.request((new Request(this)).setMoney(-money));
        }
    }
}
