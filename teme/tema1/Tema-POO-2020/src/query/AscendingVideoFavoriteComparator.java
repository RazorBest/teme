package query;

import java.util.Comparator;

import entertainment.Video;

public final class AscendingVideoFavoriteComparator implements Comparator<Video> {

    @Override
    public int compare(final Video video1, final Video video2) {
        if (video1.getFavorite() != video2.getFavorite()) {
            return video1.getFavorite() - video2.getFavorite();
        }
        return video1.getTitle().compareTo(video2.getTitle());
    }

}
