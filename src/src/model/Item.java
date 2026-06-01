package model;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemID;
    private String barcode;
    private String status;
    private String bookID;
    private String shelfID;

    public Item() {
    }

    public Item(String itemID, String barcode, String status, String bookID, String shelfID) {
        this.itemID = itemID;
        this.barcode = barcode;
        this.status = status;
        this.bookID = bookID;
        this.shelfID = shelfID;
    }

    public String getItemID() { return itemID; }
    public void setItemID(String itemID) { this.itemID = itemID; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBookID() { return bookID; }
    public void setBookID(String bookID) { this.bookID = bookID; }

    public String getShelfID() { return shelfID; }
    public void setShelfID(String shelfID) { this.shelfID = shelfID; }
}
