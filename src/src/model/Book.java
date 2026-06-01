package model;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookID;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int publishYear;
    private String ddcCode;
    private double price;
    private String summary;

    public Book() {
    }

    public Book(String bookID, String title, String author, String isbn, String publisher,
                int publishYear, String ddcCode, double price, String summary) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.ddcCode = ddcCode;
        this.price = price;
        this.summary = summary;
    }

    // Getters and Setters
    public String getBookID() { return bookID; }
    public void setBookID(String bookID) { this.bookID = bookID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }

    public String getDdcCode() { return ddcCode; }
    public void setDdcCode(String ddcCode) { this.ddcCode = ddcCode; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
