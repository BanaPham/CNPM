package model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private double price;
    private String publisher;
    private int publishYear;
    private String ddcCode;
    private String coverImage;
    private String summary;
    private String isbn;

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

    // Mappings for bookID from root project
    public String getBookID() { return String.valueOf(id); }
    public void setBookID(String bookID) {
        if (bookID != null && !bookID.trim().isEmpty()) {
            try {
                this.id = Integer.parseInt(bookID.trim());
            } catch (NumberFormatException e) {
                // Ignore parsing errors for non-integer IDs if any
            }
        }
    }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }

    public String getDdcCode() { return ddcCode; }
    public void setDdcCode(String ddcCode) { this.ddcCode = ddcCode; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
