package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
