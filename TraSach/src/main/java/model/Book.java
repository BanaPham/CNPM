package model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private double coverPrice;

    public Book() {
        super();
    }

    public Book(String title, double coverPrice) {
        super();
        this.title = title;
        this.coverPrice = coverPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getCoverPrice() { return coverPrice; }
    public void setCoverPrice(double coverPrice) { this.coverPrice = coverPrice; }
}
