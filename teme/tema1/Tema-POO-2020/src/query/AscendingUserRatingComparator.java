package query;

import java.util.Comparator;

import app.User;

public final class AscendingUserRatingComparator implements Comparator<User> {

    @Override
    public int compare(final User user1, final User user2) {
        if (user1.getRatingCount() != user2.getRatingCount()) {
            return user1.getRatingCount() - user2.getRatingCount();
        }
        return user1.getUsername().compareTo(user2.getUsername());
    }

}
