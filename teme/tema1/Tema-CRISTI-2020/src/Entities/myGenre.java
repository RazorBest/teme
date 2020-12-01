package Entities;

public class myGenre {
    String type;
    int count;

    public myGenre(String type) {
        this.type = type;
        this.count = 0;
    }

    public myGenre(String type, int count) {
        this.type = type;
        this.count = count;
    }
}
