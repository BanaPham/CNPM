package model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String barcode;
    private String status;
    private Book book;
    private Shelf shelf;
    private String bookID;
    private String shelfID;

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

    public Shelf getShelf() { return shelf; }
    public void setShelf(Shelf shelf) { this.shelf = shelf; }

    // Mappings for itemID from root project
    public String getItemID() { return String.valueOf(id); }
    public void setItemID(String itemID) {
        if (itemID != null && !itemID.trim().isEmpty()) {
            try {
                this.id = Integer.parseInt(itemID.trim());
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
    }

    public String getBookID() { return bookID; }
    public void setBookID(String bookID) { this.bookID = bookID; }

    public String getShelfID() { return shelfID; }
    public void setShelfID(String shelfID) { this.shelfID = shelfID; }
}
