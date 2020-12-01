package Entities;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Comparators {
    public static class AscendComparatorAverage implements Comparator<Actor> {

        public int compare(Actor a1, Actor a2) {  // pentru query AVERAGE
            double actor1 = a1.getAverage();
            double actor2 = a2.getAverage();
            return (Double.compare(actor1, actor2));
        }
    }

    static class ActorChainedComparator implements Comparator<Actor> {   // pentru Actori

        private final List<Comparator<Actor>> listComparators;

        @SafeVarargs
        public ActorChainedComparator(Comparator<Actor>... comparators) {
            this.listComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(Actor emp1, Actor emp2) {
            for (Comparator<Actor> comparator : listComparators) {
                int result = comparator.compare(emp1, emp2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }

   public static class DescendComparatorAverage implements Comparator<Actor> {

        public int compare(Actor a1, Actor a2) {   // pentru query AVERAGE
            float actor1 = a2.getAverage();
            float actor2 = a1.getAverage();
            return (Double.compare(actor1, actor2));
        }
    }

    static class AscendComparatorAwards implements Comparator<Actor> {

        public int compare(Actor a1, Actor a2) { // pentru query AWARDS
            double actor1 = a1.getAwardsCount();
            double actor2 = a2.getAwardsCount();
            return (Double.compare(actor1, actor2));
        }
    }

    static class DescendComparatorAwards implements Comparator<Actor> {

        public int compare(Actor a1, Actor a2) {  // pentru query AWARDS
            double actor1 = a2.getAwardsCount();
            double actor2 = a1.getAwardsCount();
            return (Double.compare(actor1, actor2));
        }
    }
    // alfabetic dupa actori
    static class AscendSortByName implements Comparator<Actor> {
        public int compare(Actor a1, Actor a2) {
            return a1.name.compareTo(a2.name);
        }
    }
    // invers alfabetic dupa actori
    static class DescendSortByName implements Comparator<Actor> {
        public int compare(Actor a1, Actor a2) {
            return a2.name.compareTo(a1.name);
        }
    }

    static class AscendComparatorRating implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query RATING
            double video1 = v1.totalRating;
            double video2 = v2.totalRating;
            return (Double.compare(video1, video2));
        }
    }

    static class DescendComparatorRating implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query RATING
            double video1 = v1.totalRating;
            double video2 = v2.totalRating;
            return (Double.compare(video2, video1));
        }
    }

    static class AscendComparatorFavorite implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query FAVORITE
            int video1 = v1.contorFavorite;
            int video2 = v2.contorFavorite;
            return (Double.compare(video1, video2));
        }
    }

    static class DescendComparatorFavorite implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query FAVORITE
            int video1 = v1.contorFavorite;
            int video2 = v2.contorFavorite;
            return (Double.compare(video2, video1));
        }
    }

    static class AscendComparatorLongest implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query LONGEST
            int video1 = v1.getTotalDuration();
            int video2 = v2.getTotalDuration();
            return (Integer.compare(video1, video2));
        }
    }

    static class DescendComparatorLongest implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query LONGEST
            int video1 = v1.getTotalDuration();
            int video2 = v2.getTotalDuration();
            return (Integer.compare(video2, video1));
        }
    }

    static class AscendComparatorViewed implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query MOST_VIEWED
            double video1 = v1.contorView;
            double video2 = v2.contorView;
            return (Double.compare(video1, video2));
        }
    }

    static class DescendComparatorViewed implements Comparator<Video> {

        public int compare(Video v1, Video v2) { // pentru query MOST_VIEWED
            int video1 = v1.contorView;
            int video2 = v2.contorView;
            return (Double.compare(video2, video1));
        }
    }
    // dupa numarul de rating dat crescator
    static class AscendComparatorUser implements Comparator<User> {

        public int compare(User u1, User u2) { // pentru USER query
            double user1 = u1.ratingCount;
            double user2 = u2.ratingCount;
            return (Double.compare(user1, user2));
        }
    }
    // dupa numarul de rating dat descrescator
    static class DescendComparatorUser implements Comparator<User> {

        public int compare(User u1, User u2) { // pentru USER query
            int user1 = u1.ratingCount;
            int user2 = u2.ratingCount;
            return (Double.compare(user2, user1));
        }
    }
    // pentru sortarea Users cu mai multe criterii
    static class UserChainedComparator implements Comparator<User> {

        private final List<Comparator<User>> listComparators;

        @SafeVarargs
        public UserChainedComparator(Comparator<User>... comparators) {
            this.listComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(User emp1, User emp2) {
            for (Comparator<User> comparator : listComparators) {
                int result = comparator.compare(emp1, emp2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }
    // USER alfabetic dupa numele user-ului
    static class AscendSortByUserName implements Comparator<User> {
        public int compare(User a1, User a2) {
            return a1.username.compareTo(a2.username);
        }
    }
    // USER invers alfabetic dupa numele user-ului
    static class DescendSortByUserName implements Comparator<User> {
        public int compare(User a1, User a2) {
            return a2.username.compareTo(a1.username);
        }
    }

    // VIDEO alfabetic dupa titlu
    static class AscendSortByTitle implements Comparator<Video> {
        public int compare(Video v1, Video v2) {
            return v1.title.compareTo(v2.title);
        }
    }
    // VIDEO invers alfabetic dupa titlu
    static class DescendSortByTitle implements Comparator<Video> {
        public int compare(Video v1, Video v2) {
            return v2.title.compareTo(v1.title);
        }
    }
    // pentru sortarea Videos dupa mai multe criterii
    static class VideoChainedComparator implements Comparator<Video> {

        private final List<Comparator<Video>> listComparators;

        @SafeVarargs
        public VideoChainedComparator(Comparator<Video>... comparators) {
            this.listComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(Video emp1, Video emp2) {
            for (Comparator<Video> comparator : listComparators) {
                int result = comparator.compare(emp1, emp2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }
    // pentru Genre in functie de popularitatea genului
    static class DescendComparatorGenre implements Comparator<myGenre> {

        public int compare(myGenre g1, myGenre g2) {
            int genre1 = g1.count;
            int genre2 = g2.count;
            return (Integer.compare(genre2, genre1));
        }
    }
}
