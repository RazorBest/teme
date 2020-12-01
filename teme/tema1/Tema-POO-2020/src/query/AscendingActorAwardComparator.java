package query;

import java.util.Comparator;

import actor.Actor;

public final class AscendingActorAwardComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor actor1, final Actor actor2) {
        if (actor1.getAwardCount() != actor2.getAwardCount()) {
            return actor1.getAwardCount() - actor2.getAwardCount();
        }
        return actor1.getName().compareTo(actor2.getName());
    }

}
