package app;

import java.util.LinkedList;
import java.util.List;

import entertainment.Video;

public final class Genre {
    private final String name;
    private final List<Video> videoArray;
    private int views = 0;

    public Genre(final String name) {
        this.name = name;
        this.videoArray = new LinkedList<Video>();
    }

    /**
     * Adds a video that has the genre represented by this object.
     * @param video
     */
    public void addVideo(final Video video) {
        videoArray.add(video);
    }

    /**
     * Computes the total number of views of the videos stored for this genre object.
     * Must be always called after a video is added or changes its views.
     * Since this object is not responsible for the views of the videos,
     * the entity that changes the views of the video must call this method by itself.
     */
    public void computeViews() {
        views = 0;
        for (Video video : videoArray) {
            views += video.getViews();
        }
    }

    public List<Video> getVideoArray() {
        return videoArray;
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @return  the total number of views for this specific genre
     *          must call computeViews() before calling this method
     */
    public int getViews() {
        return views;
    }
}
