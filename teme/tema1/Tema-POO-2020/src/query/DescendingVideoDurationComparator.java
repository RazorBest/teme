package query;

import java.util.Comparator;

import entertainment.Video;

public final class DescendingVideoDurationComparator implements Comparator<Video> {

    @Override
    public int compare(final Video video1, final Video video2) {
        if (video2.getDuration() - video1.getDuration() != 0) {
            return video2.getDuration() - video1.getDuration();
        }
        return video2.getTitle().compareTo(video1.getTitle());
    }

}
