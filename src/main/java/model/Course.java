package model;

public class Course {
    private int id;
    private String title;
    private int credits;

    public Course() {
    }

    public Course(int id, String title, int credits) {
        this.id = id;
        this.title = title;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }
}
