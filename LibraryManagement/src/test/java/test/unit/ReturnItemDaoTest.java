package test.unit;

import java.sql.Connection;
import java.sql.Statement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dao.DAO;
import dao.ItemDAO;
import model.Item;

public class ReturnItemDaoTest {
    private ItemDAO itemDAO = new ItemDAO();
    private Connection con;

    @Before
    public void setUp() throws Exception {
        con = DAO.con;
        if (con == null) {
            new DAO();
            con = DAO.con;
        }
        
        try (Statement stmt = con.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM tblitem WHERE id = 1");
            stmt.execute("DELETE FROM tblbook WHERE id = 1");
            stmt.execute("INSERT INTO tblbook(id, title, price) VALUES(1, 'Lap trinh Java Swing', 50000.0)");
            stmt.execute("INSERT INTO tblitem(id, barcode, status, book_id) VALUES(1, 'BC001', 'Borrowed', 1)");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (con != null) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                stmt.execute("DELETE FROM tblitem WHERE id = 1");
                stmt.execute("DELETE FROM tblbook WHERE id = 1");
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
        }
    }

    @Test
    public void testSearchItemByBarcodeStandard() {
        String barcode = "BC001";
        Item item = itemDAO.searchItemByBarcode(barcode);
        Assert.assertNotNull(item);
        Assert.assertEquals("BC001", item.getBarcode());
        Assert.assertEquals("Borrowed", item.getStatus());
        Assert.assertNotNull(item.getBook());
        Assert.assertEquals("Lap trinh Java Swing", item.getBook().getTitle());
    }

    @Test
    public void testSearchItemByBarcodeException() {
        String barcode = "BC999";
        Item item = itemDAO.searchItemByBarcode(barcode);
        Assert.assertNull(item);
    }

    @Test
    public void testUpdateItemStatusStandard() {
        Item item = new Item();
        item.setId(1);
        item.setBarcode("BC001");
        item.setStatus("Borrowed");
        
        boolean result = itemDAO.updateItemStatus(item, "Ready");
        Assert.assertTrue(result);
        Assert.assertEquals("Ready", item.getStatus());
        
        // Khôi phục lại trạng thái cũ trong database để tránh ảnh hưởng tới test case khác
        itemDAO.updateItemStatus(item, "Borrowed");
    }
}
