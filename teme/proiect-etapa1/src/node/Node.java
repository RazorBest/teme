package node;

import java.util.LinkedList;
import java.util.List;

import dataflow.Request;

public abstract class Node {
    private final long id;

    protected List<Node> sources;
    protected boolean out = false;

    public Node(final long id) {
        this.id = id;
    }

    public void update(final Iterable<Node> nodes) {
        sources = new LinkedList<Node>();
        for (final Node node : nodes) {
            if (node.isOut()) {
                continue;
            }
            sources.add(node);
        }
    }

    public void update() {

    }

    public void request(final Request request) {

    }

    public void request() {
    }

    public List<Node> getSources() {
        return sources;
    }

    public void setOut(final boolean out) {
        this.out = out;
    }

    public boolean isOut() {
        return out;
    }

    public long getId() {
        return id;
    }

    @Override
    public abstract String toString();
}
