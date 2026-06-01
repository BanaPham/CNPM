package dao;

import model.Item;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ItemDAO extends DAO {

    public ItemDAO() {
        super();
    }

    public ArrayList<Item> createItems(String bookID, String shelfID, int quantity) {
        ArrayList<Item> result = new ArrayList<>();
        String sql = "INSERT INTO tblItem (itemID, barcode, status, bookID, shelfID) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            // Lặp theo số lượng để tạo các bản sao
            for (int i = 0; i < quantity; i++) {
                String itemID = "ITM" + System.currentTimeMillis() + i;
                String barcode = "BC-" + itemID;

                ps.setString(1, itemID);
                ps.setString(2, barcode);
                ps.setString(3, "Available");
                ps.setString(4, bookID);
                ps.setString(5, shelfID);

                ps.executeUpdate();

                // Đóng gói đối tượng Item đưa vào list trả về
                Item item = new Item();
                item.setItemID(itemID);
                item.setBarcode(barcode);
                item.setStatus("Available");
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
