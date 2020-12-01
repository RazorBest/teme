package query;

import java.util.Comparator;

import app.User;

public final class AscendingUserNameComparator implements Comparator<User> {

    @Override
    public int compare(final User user1, final User user2) {
        return user1.getUsername().compareTo(user2.getUsername());
        // What if they are equal?
    }

}
