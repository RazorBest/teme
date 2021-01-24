package node;

import dataflow.Request;

public abstract class Node {
    private final long id;
    // By default, the update method will not link with this node, if out is true
    protected boolean out = false;

    public Node(final long id) {
        this.id = id;
    }

    /**
     * This method can be overwritten by subclasses, if needed.
     *
     */
    public void update() {

    }

    /**
     * This method can be overwritten by subclasses, if needed. It handles a request
     *
     */
    public void request(final Request request) {

    }

    /**
     * This method can be overwritten by subclasses, if needed. This method is used
     * as a request generator. It doesn't receive requests. It just generates
     * requests.
     */
    public void request() {
    }

    public final long getId() {
        return id;
    }

    /**
     * If true, it will not be linked to other nodes in the future. This behaviour
     * is not guaranteed if custom classes overwrite the update(Node) method
     *
     * @param out
     */
    public void setOut(final boolean out) {
        this.out = out;
    }

    /**
     *
     * @return - true, if node is out
     */
    public boolean isOut() {
        return out;
    }
}
