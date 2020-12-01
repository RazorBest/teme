package query;

import java.util.Comparator;

import app.User;

public final class DescendingUserRatingComparator implements Comparator<User> {

    @Override
    public int compare(final User user1, final User user2) {
        if (user2.getRatingCount() != user1.getRatingCount()) {
            return user2.getRatingCount() - user1.getRatingCount();
        }
        return user2.getUsername().compareTo(user1.getUsername());
    }

}
