package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entertainment.Video;
import fileio.ActionInputData;
import fileio.JsonHolyWriter;
import query.AscendingVideoRatingComparator;

public final class Recommendation {
    private final AppHandler app;
    private final Map<String, User> userMap;
    private final Video[] videoArray;
    private final Genre[] genreArray;

    private final JsonHolyWriter writer;

    public Recommendation(final AppHandler app, final JsonHolyWriter writer) {
        this.app = app;
        this.writer = writer;

        userMap = app.getUserMap();
        videoArray = app.getVideoArray();

        genreArray = createGenreArray();
    }

    private Genre[] createGenreArray() {
        Map<String, Genre> genreMap = new HashMap<String, Genre>();
        Genre[] genreArr;

        for (Video video : videoArray) {
            for (String genreName : video.getGenres()) {
                Genre genre;
                if (!genreMap.containsKey(genreName)) {
                    genre = new Genre(genreName);
                    genreMap.put(genreName, genre);
                } else {
                    genre = genreMap.get(genreName);
                }
                genre.addVideo(video);
            }
        }

        genreArr = genreMap.values().toArray(new Genre[genreMap.size()]);

        return genreArr;
    }

    /**
     * Decides what type of recommendation to apply. Call the app.verifyRecommendation
     *  to check if the recommendation is appliable for the user. Then, run the action.
     *
     * @param actionData    data containing info about the action
     */
    public void run(final ActionInputData actionData) {
        final ActionStatus status = app.verifyRecommendation(actionData.getType(),
                actionData.getUsername());
        final String type = actionData.getType();
        final String username = actionData.getUsername();
        String message = "";

        if (status == ActionStatus.SEARCH_NOT_PREMIUM) {
            message = "SearchRecommendation cannot be applied!";
        } else if (status == ActionStatus.FAVORITE_NOT_PREMIUM) {
            message = "FavoriteRecommendation cannot be applied!";
        } else if (status == ActionStatus.POPULAR_NOT_PREMIUM) {
            message = "PopularRecommendation cannot be applied!";
        } else {
            switch (type) {
            case "standard":
                message = standard(username);
                break;
            case "best_unseen":
                message = bestUnseen(username);
                break;
            case "popular":
                message = popular(username);
                break;
            case "favorite":
                message = favorite(username);
                break;
            case "search":
                message = search(username, actionData.getGenre());
                break;
            default:
                return;
            }
        }

        try {
            writer.holyWriteFile(actionData.getActionId(), "message", message);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String standard(final String username) {
        final Map<String, Integer> history = userMap.get(username).getHistory();

        for (final Video video : videoArray) {
            final String title = video.getTitle();
            if (!history.containsKey(title)) {
                return "StandardRecommendation result: " + title;
            }
        }

        return "StandardRecommendation cannot be applied!";
    }

    private String bestUnseen(final String username) {
        final Map<String, Integer> history = userMap.get(username).getHistory();
        final ArrayList<Video> videos = new ArrayList<Video>(Arrays.asList(videoArray));

        videos.sort((video1, video2) -> Double.compare(video2.getRating(), video1.getRating()));

        for (final Video video : videos) {
            final String title = video.getTitle();
            if (!history.containsKey(title)) {
                return "BestRatedUnseenRecommendation result: " + title;
            }
        }

        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    private String popular(final String username) {
        User user = userMap.get(username);
        Map<String, Integer> history = user.getHistory();

        for (Genre genre : genreArray) {
            genre.computeViews();
        }

        Arrays.sort(genreArray,
                ((genre1, genre2) -> Integer.compare(genre2.getViews(), genre1.getViews())));

        for (Genre genre : genreArray) {
            for (Video video : genre.getVideoArray()) {
                if (!history.containsKey(video.getTitle())) {
                    return "PopularRecommendation result: " + video.getTitle();
                }
            }
        }


        return "PopularRecommendation cannot be applied!";
    }

    private String favorite(final String username) {
        final User user = userMap.get(username);
        final Map<String, Integer> history = user.getHistory();
        final ArrayList<Video> videos = new ArrayList<Video>(Arrays.asList(videoArray));

        videos.sort(
                (video1, video2) -> Integer.compare(video2.getFavorite(), video1.getFavorite())
                );

        final Iterator<Video> it = videos.iterator();
        while (it.hasNext()) {
            final Video video = it.next();
            if (history.containsKey(video.getTitle())) {
                it.remove();
            }
        }

        if (videos.size() > 0 && videos.get(0).getFavorite() > 0) {
            return "FavoriteRecommendation result: " + videos.get(0).getTitle();
        }

        return "FavoriteRecommendation cannot be applied!";
    }

    private String search(final String username, final String genre) {
        final User user = userMap.get(username);
        final Map<String, Integer> history = user.getHistory();
        final ArrayList<Video> videos = new ArrayList<Video>(Arrays.asList(videoArray));

        videos.sort(new AscendingVideoRatingComparator());
        videos.removeIf(video -> !video.getGenres().contains(genre));

        final Iterator<Video> it = videos.iterator();
        while (it.hasNext()) {
            final Video video = it.next();

            if (history.containsKey(video.getTitle())) {
                it.remove();
            }
        }

        if (videos.size() > 0) {
            return "SearchRecommendation result: " + videos.toString();
        }

        return "SearchRecommendation cannot be applied!";
    }
}
