package strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import node.Node;

public final class DefaultNodeStrategy<T extends Node> implements NodeLinkStrategy<T> {

    private List<T> sources;

    @Override
    public void applyStrategy(final Collection<T> candidateSources) {
        sources = new ArrayList<T>(candidateSources);
        sources.removeIf(node -> node.isOut());
    }

    @Override
    public List<T> getSources() {
        return sources;
    }

}
