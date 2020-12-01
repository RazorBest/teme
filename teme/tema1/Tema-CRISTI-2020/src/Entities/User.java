package Entities;

import java.util.*;

public class User {
    public int ratingCount = 0;
    public String username;
    public String subscriptionType;
    public Map<String, Integer> history;
    public Map<String, Video> viewedVideos;
    public ArrayList<Video> favoriteVideos = new ArrayList<>();
    public ArrayList<Video> videos = new ArrayList<>();

    public User(String username, String subscriptionType, Map<String,
                Integer> history, ArrayList<Video> FavoriteVideos,
                ArrayList<Video> videos) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this. history = history;
        this.favoriteVideos = FavoriteVideos;
        this.viewedVideos = new HashMap<>();
        for (Video video : videos) {
            assert false;
            this.videos.add(video.clone());
        }
    }

    public User(User user) {
        this.username = user.username;
        this.subscriptionType = user.subscriptionType;
        this. history = user.history;
        this.favoriteVideos = user.favoriteVideos;
        this.viewedVideos = user.viewedVideos;
        for (Video video : user.videos) {
            assert false;
            this.videos.add(video.clone());
        }
    }

    // constructia mapei de genuri din array
    public Map<String, Integer> setGenreMap(ArrayList<String> list) {
        Map<String, Integer> genreMap = new HashMap<>();
        for (String genreString : list) {
            //myGenre newGenre = new myGenre(genreString);
            genreMap.put(genreString, 0);
        }
        return genreMap;
    }
    public Map<String, Integer> genreListConstruction() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("TV Movie",
                "Drama", "Fantasy", "Comedy", "Family", "War", "Sci-Fi & Fantasy",
                "Crime", "Animation", "Science Fiction", "Action", "Horror",
                "Mystery", "Western", "Adventure", "Action & Adventure", "Romance",
                "Thriller", "Kids", "History"));
        return setGenreMap(list);
    }

    // punem din history in viewVideos si
    // actualizam contorView al filmului pwntru user
    public void setUserView(Map<String, Integer> map) {
        for (String title : map.keySet()) {
            for (Video video : this.videos) {
                if (video.title.equals(title)) {          // cauta dupa titlu
                    video.contorView += map.get(title);   // de cate ori a vazut user
                    this.viewedVideos.put(video.title, video); // adaugam in viewed
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteVideos + '}';
    }

}
