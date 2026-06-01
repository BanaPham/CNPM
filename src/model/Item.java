package model;

public class Item {
    private String itemID;
    private String barcode;
    private String status;
    private Book book;   // Mối quan hệ Composition với Book
    private Shelf shelf; // Mối quan hệ Aggregation với Shelf

    public Item() {
    }

    public Item(String itemID, String barcode, String status, Book book, Shelf shelf) {
        this.itemID = itemID;
        this.barcode = barcode;
        this.status = status;
        this.book = book;
        this.shelf = shelf;
    }

    public String getItemID() { return itemID; }
    public void setItemID(String itemID) { this.itemID = itemID; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Shelf getShelf() { return shelf; }
    public void setShelf(Shelf shelf) { this.shelf = shelf; }
}
