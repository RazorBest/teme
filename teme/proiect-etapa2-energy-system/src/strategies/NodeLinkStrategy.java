package strategies;

import java.util.Collection;
import java.util.List;

import node.Node;

public interface NodeLinkStrategy<T extends Node> {
    /**
     * Applies the respective strategy.
     *
     * @param candidateSources
     */
    void applyStrategy(Collection<T> candidateSources);

    /**
     * @return the sources that were chosen after the applyStrategy call.
     */
    List<T> getSources();
}
