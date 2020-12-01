package query;

import java.util.Arrays;
import java.util.Comparator;

import app.User;
import fileio.ActionInputData;

public final class UserQueryHandler extends QueryHandler<User> {

    public UserQueryHandler(final User[] array) {
        super(array);

        Arrays.sort(this.array, new AscendingUserNameComparator());

        this.setNoPostFilters(true);
    }

    @Override
    protected Comparator<User> getComparator(final ActionInputData actionData) {
        final String criteria = actionData.getCriteria();
        final String sortType = actionData.getSortType();

        if (sortType.equals("asc")) {
            switch (criteria) {
            case "num_ratings":
                return new AscendingUserRatingComparator();
            default:
                return null;
            }
        } else {
            switch (criteria) {
            case "num_ratings":
                return new DescendingUserRatingComparator();
            default:
                return null;
            }
        }
    }

    @Override
    public void setFilters(final ActionInputData actionData) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean postFilter(final User user) {
        return false;
    }

    @Override
    public boolean preFilter(final User user) {
        // TODO Auto-generated method stub
        return user.getRatingCount() > 0;
    }

}
