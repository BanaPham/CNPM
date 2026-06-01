package test;

import dao.ItemDAO;
import model.Item;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ItemDaoTest {

    // Test trường hợp 3: Sinh bản sao với dữ liệu hợp lệ (Số lượng = 2)
    @Test
    public void testCreateItems_Success() {
        ItemDAO itemDAO = new ItemDAO();
        // Giả sử mã B001 và SH001 đã tồn tại trong DB test
        String bookID = "B001";
        String shelfID = "SH001";
        int quantity = 2;

        ArrayList<Item> listGenerated = itemDAO.createItems(bookID, shelfID, quantity);

        // Đảm bảo mảng trả về không rỗng
        Assert.assertNotNull(listGenerated);
        // Đảm bảo sinh đúng số lượng (2 bản sao)
        Assert.assertEquals(2, listGenerated.size());
        // Kiểm tra trạng thái mặc định của bản sao đầu tiên là "Available"
        Assert.assertEquals("Available", listGenerated.get(0).getStatus());
        // Kiểm tra định dạng Barcode
        Assert.assertTrue(listGenerated.get(0).getBarcode().startsWith("BC-"));
    }

    // Test trường hợp 4: Truyền vào số lượng bằng 0
    @Test
    public void testCreateItems_Fail_ZeroQuantity() {
        ItemDAO itemDAO = new ItemDAO();
        String bookID = "B001";
        String shelfID = "SH001";
        int quantity = 0;

        ArrayList<Item> listGenerated = itemDAO.createItems(bookID, shelfID, quantity);

        // Đảm bảo mảng trả về rỗng vì không có vòng lặp nào chạy
        Assert.assertNotNull(listGenerated);
        Assert.assertEquals(0, listGenerated.size());
    }
}
