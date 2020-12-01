package query;

import java.util.Comparator;

import actor.Actor;

public final class AscendingActorNameComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor actor1, final Actor actor2) {
        return actor1.getName().compareTo(actor2.getName());
        // What if they are equal?
    }

}
