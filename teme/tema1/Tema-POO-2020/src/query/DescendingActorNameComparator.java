package query;

import java.util.Comparator;

import actor.Actor;

public final class DescendingActorNameComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor actor1, final Actor actor2) {
        return actor2.getName().compareTo(actor1.getName());
        // What if they are equal?
    }

}
