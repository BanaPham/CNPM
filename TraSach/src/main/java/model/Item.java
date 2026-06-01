package model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String barcode;
    private String status;
    private Book book;

    public Item() {
        super();
    }

    public Item(String barcode, String status, Book book) {
        super();
        this.barcode = barcode;
        this.status = status;
        this.book = book;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
