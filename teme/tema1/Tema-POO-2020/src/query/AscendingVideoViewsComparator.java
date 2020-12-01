package query;

import java.util.Comparator;

import entertainment.Video;

public final class AscendingVideoViewsComparator implements Comparator<Video> {

    @Override
    public int compare(final Video video1, final Video video2) {
        if (video1.getViews() - video2.getViews() != 0) {
            return video1.getViews() - video2.getViews();
        }
        return video1.getTitle().compareTo(video2.getTitle());
    }

}
