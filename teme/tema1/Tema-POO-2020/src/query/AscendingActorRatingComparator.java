package query;

import java.util.Comparator;

import actor.Actor;

public final class AscendingActorRatingComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor actor1, final Actor actor2) {
        if (Double.compare(actor1.getRating(), actor2.getRating()) != 0) {
            return Double.compare(actor1.getRating(), actor2.getRating());
        }

        return actor1.getName().compareTo(actor2.getName());
    }

}
