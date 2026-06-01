package model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private float price;

    public Book() {
        super();
    }

    public Book(String title, String author, float price) {
        super();
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}