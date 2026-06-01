package test;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import dao.ItemDAO;
import model.Item;

public class ItemDaoTest {

    // Test trường hợp 3: Mã sách CÓ bản sao trong thư viện ("B001")
    @Test
    public void testCheckAvailability_Found() {
        ItemDAO itemDAO = new ItemDAO();
        ArrayList<Item> listItem = itemDAO.checkAvailability("B001");
        
        Assert.assertNotNull(listItem);
        // Trong script SQL của chúng ta, sách B001 có đúng 3 bản sao (IT001, IT002, IT003)
        Assert.assertEquals(3, listItem.size()); 
        // Kiểm tra xem có đóng gói được vị trí Kệ sách (Shelf) đi kèm không
        Assert.assertNotNull(listItem.get(0).getShelf().getRoom()); 
    }

    // Test trường hợp 4: Mã sách KHÔNG TỒN TẠI ("B999")
    @Test
    public void testCheckAvailability_NotFound() {
        ItemDAO itemDAO = new ItemDAO();
        ArrayList<Item> listItem = itemDAO.checkAvailability("B999");
        
        Assert.assertNotNull(listItem);
        Assert.assertEquals(0, listItem.size()); // Đảm bảo mảng rỗng
    }
}