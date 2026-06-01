package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Item;
import model.Shelf;

public class ItemDAO extends DAO {

    public ItemDAO() {
        super();
    }

    public ArrayList<Item> checkAvailability(String bookID) {
        ArrayList<Item> result = new ArrayList<>();
        if (bookID == null) return result;
        bookID = bookID.trim(); // Trim spaces

        // Standardized query for SQL Server - Use LEFT JOIN to show items even without shelf
        String sql = "SELECT i.itemID, i.barcode, i.status, s.shelfID, s.room, s.row " +
                "FROM tblItem i " +
                "LEFT JOIN tblShelf s ON i.shelfID = s.shelfID " +
                "WHERE i.bookID = ?";

        try {
            if (con == null || con.isClosed()) {
                System.err.println("ItemDAO: Database connection is null or closed!");
                return result;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setNString(1, bookID); // Use setNString for SQL Server Unicode support

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getString("itemID") != null ? rs.getString("itemID").trim() : "");
                item.setBarcode(rs.getString("barcode") != null ? rs.getString("barcode").trim() : "");
                item.setStatus(rs.getString("status") != null ? rs.getString("status").trim() : "");

                Shelf shelf = new Shelf();
                shelf.setShelfID(rs.getString("shelfID"));
                shelf.setRoom(rs.getString("room"));
                shelf.setRow(rs.getString("row"));

                item.setShelf(shelf);
                result.add(item);
            }
            System.out.println("ItemDAO: Querying bookID [" + bookID + "] - Found: " + result.size() + " items.");
        } catch (Exception e) {
            System.err.println("ItemDAO error querying bookID " + bookID + ": " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
