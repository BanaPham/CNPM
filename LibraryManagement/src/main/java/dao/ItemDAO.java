package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Item;
import model.Book;

public class ItemDAO extends DAO {
    public ItemDAO() {
        super();
    }

    // Method used by MuonSach (Borrow)
    public Item searchItem(String barcode) {
        Item item = null;
        String sql = "SELECT i.id as item_id, i.barcode, i.status, i.price, b.id as book_id, b.title, b.author " +
                     "FROM tblitem i INNER JOIN tblbook b ON i.book_id = b.id " +
                     "WHERE i.barcode = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getDouble("price")); // maps i.price to Book's price field

                item = new Item();
                item.setId(rs.getInt("item_id"));
                item.setBarcode(rs.getString("barcode"));
                item.setStatus(rs.getString("status"));
                item.setBook(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // Method used by TraSach (Return)
    public Item searchItemByBarcode(String barcode) {
        Item item = null;
        String sql = "SELECT i.id as item_id, i.barcode, i.status, b.id as book_id, b.title, b.price " +
                     "FROM tblitem i INNER JOIN tblbook b ON i.book_id = b.id " +
                     "WHERE i.barcode = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setCoverPrice(rs.getDouble("price")); // maps b.price to Book's coverPrice field

                item = new Item();
                item.setId(rs.getInt("item_id"));
                item.setBarcode(rs.getString("barcode"));
                item.setStatus(rs.getString("status"));
                item.setBook(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // Method used by TraSach (Return)
    public boolean updateItemStatus(Item item, String status) {
        boolean result = false;
        String sql = "UPDATE tblitem SET status = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, item.getId());
            int count = ps.executeUpdate();
            if (count > 0) {
                item.setStatus(status);
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method used by OPAC Check Availability (from root src)
    public ArrayList<Item> checkAvailability(String bookID) {
        ArrayList<Item> result = new ArrayList<>();
        if (bookID == null) return result;
        bookID = bookID.trim();

        String sql = "SELECT i.id as item_id, i.barcode, i.status, s.shelf_id, s.room, s.row " +
                     "FROM tblitem i " +
                     "LEFT JOIN tblshelf s ON i.shelf_id = s.id " +
                     "WHERE i.book_id = ?";

        try {
            if (con == null || con.isClosed()) {
                System.err.println("ItemDAO: Database connection is null or closed!");
                return result;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(bookID));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("item_id"));
                item.setBarcode(rs.getString("barcode") != null ? rs.getString("barcode").trim() : "");
                item.setStatus(rs.getString("status") != null ? rs.getString("status").trim() : "");

                model.Shelf shelf = new model.Shelf();
                shelf.setShelfID(rs.getString("shelf_id"));
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

    private int extractIntId(String strId) {
        if (strId == null) return 0;
        String numeric = strId.replaceAll("[^0-9]", "");
        if (!numeric.isEmpty()) {
            try {
                return Integer.parseInt(numeric);
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return 0;
    }

    public ArrayList<Item> createItems(String bookID, String shelfID, int quantity) {
        ArrayList<Item> result = new ArrayList<>();
        String sql = "INSERT INTO tblitem (barcode, status, price, book_id, shelf_id) VALUES (?, ?, ?, ?, ?)";

        double price = 0.0;
        try {
            PreparedStatement psBook = con.prepareStatement("SELECT price FROM tblbook WHERE id = ?");
            psBook.setInt(1, extractIntId(bookID));
            ResultSet rsBook = psBook.executeQuery();
            if (rsBook.next()) {
                price = rsBook.getDouble("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < quantity; i++) {
                String itemID = "ITM" + System.currentTimeMillis() + i;
                String barcode = "BC-" + itemID;

                ps.setString(1, barcode);
                ps.setString(2, "Ready");
                ps.setDouble(3, price);
                ps.setInt(4, extractIntId(bookID));
                ps.setInt(5, extractIntId(shelfID));

                ps.executeUpdate();
                ResultSet rsKeys = ps.getGeneratedKeys();
                int insertedId = 0;
                if (rsKeys.next()) {
                    insertedId = rsKeys.getInt(1);
                }

                Item item = new Item();
                item.setId(insertedId);
                item.setBarcode(barcode);
                item.setStatus("Ready");
                item.setBookID(bookID);
                item.setShelfID(shelfID);
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
