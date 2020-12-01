package query;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActionInputData;
import utils.Utils;

public final class ActorQueryHandler extends QueryHandler<Actor> {

    private List<String> words;
    private List<ActorsAwards> awards;
    private boolean ratingSearch = false;

    public ActorQueryHandler(final Actor[] array) {
        super(array);

        Arrays.sort(this.array, new AscendingActorNameComparator());

        this.setNoPreFilters(true);
    }

    @Override
    protected Comparator<Actor> getComparator(final ActionInputData actionData) {
        final String criteria = actionData.getCriteria();
        final String sortType = actionData.getSortType();

        if (sortType.equals("asc")) {
            switch (criteria) {
            case "awards":
                return new AscendingActorAwardComparator();
            case "average":
                return new AscendingActorRatingComparator();
            case "filter_description":
                return new AscendingActorNameComparator();
            default:
                return null;
            }
        } else {
            switch (criteria) {
            case "awards":
                return new DescendingActorAwardComparator();
            case "average":
                return new DescendingActorRatingComparator();
            case "filter_description":
                return new DescendingActorNameComparator();
            default:
                return null;
            }
        }
    }

    @Override
    public void setFilters(final ActionInputData actionData) {
        final List<List<String>> filters = actionData.getFilters();
        final int wordsIndex = 2;
        final int awardsIndex = 3;

        List<String> filter;
        boolean noPostFilters = true;

        words = new LinkedList<String>();
        filter = filters.get(wordsIndex);
        if (filter != null) {
            words = new LinkedList<String>(filter);
            noPostFilters = false;
        }

        awards = new LinkedList<ActorsAwards>();
        filter = filters.get(awardsIndex);
        if (filter != null) {
            for (final String awardString : filter) {
                awards.add(Utils.stringToAwards(awardString));
            }
            noPostFilters = false;
        }

        this.setNoPostFilters(noPostFilters);

        this.setNoPreFilters(true);
        ratingSearch = false;
        if (actionData.getCriteria().equals("average")) {
            ratingSearch = true;
            this.setNoPreFilters(false);
        }
    }

    @Override
    public boolean postFilter(final Actor actor) {
        Pattern pattern;
        Matcher matcher;

        // System.out.println("fitler " + actor);

        for (final String word : words) {
            pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(actor.getCareerDescription());

            if (!matcher.find()) {
                return false;
            }
        }

        // remember: case insensitive
        for (final ActorsAwards award : awards) {
            if (!actor.getAwards().containsKey(award)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean preFilter(final Actor actor) {
        if (!ratingSearch) {
            return true;
        }

        return actor.getRating() > 0;
    }

}
