package model;

public class Book {
    private String bookID;
    private String title;
    private String author;
    private String publisher;
    private int publishYear;
    private String ddcCode;
    private String coverImage;
    private String summary;
    private double price;

    public Book() {
    }

    public Book(String bookID, String title, String author, String publisher, 
                int publishYear, String ddcCode, String coverImage, String summary, double price) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.ddcCode = ddcCode;
        this.coverImage = coverImage;
        this.summary = summary;
        this.price = price;
    }

    // --- Getters và Setters ---
    public String getBookID() { return bookID; }
    public void setBookID(String bookID) { this.bookID = bookID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

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

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}