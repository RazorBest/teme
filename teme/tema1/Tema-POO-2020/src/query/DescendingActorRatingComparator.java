package query;

import java.util.Comparator;

import actor.Actor;

public final class DescendingActorRatingComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor actor1, final Actor actor2) {
        if (Double.compare(actor2.getRating(), actor1.getRating()) != 0) {
            return Double.compare(actor2.getRating(), actor1.getRating());
        }

        return actor2.getName().compareTo(actor1.getName());
    }

}
