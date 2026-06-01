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
        String sql = "SELECT i.itemID, i.barcode, i.status, s.shelfID, s.room, s.row_name " +
                     "FROM tblItem i " +
                     "JOIN tblShelf s ON i.shelfID = s.shelfID " +
                     "WHERE i.bookID = ?";
                     
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bookID);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getString("itemID"));
                item.setBarcode(rs.getString("barcode"));
                item.setStatus(rs.getString("status"));
                
                // Khởi tạo và đóng gói thực thể Shelf tương ứng
                Shelf shelf = new Shelf();
                shelf.setShelfID(rs.getString("shelfID"));
                shelf.setRoom(rs.getString("room"));
                shelf.setRow(rs.getString("row_name"));
                
                // Gài đối tượng kệ vào bên trong đối tượng bản sao sách
                item.setShelf(shelf);
                
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}