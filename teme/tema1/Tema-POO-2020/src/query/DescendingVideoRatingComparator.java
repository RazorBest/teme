package query;

import java.util.Comparator;

import entertainment.Video;

public final class DescendingVideoRatingComparator implements Comparator<Video> {

    @Override
    public int compare(final Video video1, final Video video2) {
        if (Double.compare(video2.getRating(), video1.getRating()) != 0) {
            return Double.compare(video2.getRating(), video1.getRating());
        }
        return video1.getTitle().compareTo(video2.getTitle());
    }

}
