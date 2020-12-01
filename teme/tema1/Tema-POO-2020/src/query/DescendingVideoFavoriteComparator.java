package query;

import java.util.Comparator;

import entertainment.Video;

public final class DescendingVideoFavoriteComparator implements Comparator<Video> {

    @Override
    public int compare(final Video video1, final Video video2) {
        if (video2.getFavorite() != video1.getFavorite()) {
            return video2.getFavorite() - video1.getFavorite();
        }
        return video2.getTitle().compareTo(video1.getTitle());
    }

}
