package query;

import java.util.Comparator;

import entertainment.Video;

public final class AscendingVideoDurationComparator implements Comparator<Video> {

    @Override
    public int compare(final Video video1, final Video video2) {
        if (video1.getDuration() - video2.getDuration() != 0) {
            return video1.getDuration() - video2.getDuration();
        }
        return video1.getTitle().compareTo(video2.getTitle());
    }

}
