package node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import strategies.DefaultNodeStrategy;
import strategies.NodeLinkStrategy;

public abstract class Receiver<T extends Node> extends Node {
    protected List<T> sources;
    private final NodeLinkStrategy<T> linkStrategy;

    public Receiver(final long id) {
        super(id);

        this.sources = new LinkedList<T>();
        linkStrategy = new DefaultNodeStrategy<T>();
    }

    public Receiver(final long id, final NodeLinkStrategy<T> linkStrategy) {
        super(id);

        this.sources = new LinkedList<T>();
        this.linkStrategy = linkStrategy;
    }

    /**
     * This method can be overwritten by subclasses
     *
     * @return
     */
    public boolean canChoose() {
        return true;
    }

    /**
     * This method can be overwritten by subclasses
     *
     * @return
     */
    public void afterChoose() {

    }

    /**
     * Chooses a new list of sources from the given ones.
     *
     * @param sinks the soures from which to choose
     */
    public final void choose(final Collection<T> sinks) {
        if (!canChoose()) {
            return;
        }

        linkStrategy.applyStrategy(sinks);
        sources = linkStrategy.getSources();

        afterChoose();
    }

    /**
     * Return the sources linked to this node.
     *
     * @return
     */
    public List<T> getSources() {
        return sources;
    }
}
