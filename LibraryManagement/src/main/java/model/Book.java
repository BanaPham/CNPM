package model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private double price;

    public Book() {
        super();
    }

    public Book(String title, String author, double price) {
        super();
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Constructor used by TraSach
    public Book(String title, double coverPrice) {
        super();
        this.title = title;
        this.price = coverPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    // Support for MuonSach price
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Support for TraSach coverPrice
    public double getCoverPrice() { return price; }
    public void setCoverPrice(double coverPrice) { this.price = coverPrice; }
}
