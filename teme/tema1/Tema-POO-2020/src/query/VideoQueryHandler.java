package query;

import java.util.Comparator;
import java.util.List;

import entertainment.Video;
import fileio.ActionInputData;

public final class VideoQueryHandler extends QueryHandler<Video> {

    private Integer year;
    private String genre;
    private boolean viewSearch = false;
    private boolean favoriteSearch = false;
    private boolean ratingSearch = false;

    public VideoQueryHandler(final Video[] list) {
        super(list);

        this.setNoPreFilters(true);
    }

    @Override
    // Use the filters that you got from setFilters()
    public boolean postFilter(final Video video) {
        if (year != null && video.getYear() != year) {
            return false;
        }
        if (genre != null && !video.hasGenre(genre)) {
            return false;
        }

        if (viewSearch && video.getViews() == 0) {
            return false;
        }
        if (favoriteSearch && video.getFavorite() == 0) {
            return false;
        }
        if (ratingSearch && video.getRating() == 0) {
            return false;
        }

        return true;
    }

    @Override
    protected Comparator<Video> getComparator(final ActionInputData actionData) {
        final String criteria = actionData.getCriteria();
        final String sortType = actionData.getSortType();
        final String type = actionData.getType();

        if (type != null && type.equals("favorite")) {
            return new DescendingVideoViewsComparator();
        }

        if (sortType.equals("asc")) {
            switch (criteria) {
            case "longest":
                return new AscendingVideoDurationComparator();
            case "favorite":
                return new AscendingVideoFavoriteComparator();
            case "ratings":
                return new AscendingVideoRatingComparator();
            case "most_viewed":
                return new AscendingVideoViewsComparator();
            default:
                return null;
            }
        } else if (sortType.equals("desc")) {
            switch (criteria) {
            case "longest":
                return new DescendingVideoDurationComparator();
            case "favorite":
                return new DescendingVideoFavoriteComparator();
            case "ratings":
                return new DescendingVideoRatingComparator();
            case "most_viewed":
                return new DescendingVideoViewsComparator();
            default:
                return null;
            }
        }

        return null;
    }

    @Override
    public void setFilters(final ActionInputData actionData) {
        final List<List<String>> filters = actionData.getFilters();

        String filter = null;
        boolean noPostFilters = true;

        year = null;
        filter = filters.get(0).get(0);
        if (filter != null) {
            year = Integer.parseInt(filter);
            noPostFilters = false;
        }

        genre = actionData.getGenre();
        filter = filters.get(1).get(0);
        if (filter != null) {
            genre = filter;
            noPostFilters = false;
        }

        viewSearch = false;
        if (actionData.getCriteria().equals("most_viewed")) {
            viewSearch = true;
            noPostFilters = false;
        }

        favoriteSearch = false;
        if (actionData.getCriteria().equals("favorite")) {
            favoriteSearch = true;
            noPostFilters = false;
        }

        ratingSearch = false;
        if (actionData.getCriteria().equals("ratings")) {
            ratingSearch = true;
            noPostFilters = false;
        }

        this.setNoPostFilters(noPostFilters);
    }

    @Override
    public boolean preFilter(final Video obj) {
        // TODO Auto-generated method stub
        return false;
    }

}
