package test.unit;

import dao.ItemDAO;
import model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDaoTest {
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        new dao.DAO();
        conn = dao.DAO.con;
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM tblitem WHERE barcode LIKE 'BC-ITM%'");
            st.executeUpdate("DELETE FROM tblshelf WHERE id = 999");
            st.executeUpdate("DELETE FROM tblbook WHERE id = 999");

            st.executeUpdate("INSERT INTO tblbook (id, title, isbn, price) VALUES (999, 'Test Book', 'ISBN999', 50000)");
            st.executeUpdate("INSERT INTO tblshelf (id, shelf_id, room, `row`) VALUES (999, 'S999', 'Room 999', 'Row 999')");
        }
    }

    @After
    public void tearDown() throws Exception {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM tblitem WHERE barcode LIKE 'BC-ITM%'");
            st.executeUpdate("DELETE FROM tblshelf WHERE id = 999");
            st.executeUpdate("DELETE FROM tblbook WHERE id = 999");
        }
    }

    // Test trường hợp 3: Sinh bản sao với dữ liệu hợp lệ (Số lượng = 2)
    @Test
    public void testCreateItems_Success() {
        ItemDAO itemDAO = new ItemDAO();
        String bookID = "B999";
        String shelfID = "S999";
        int quantity = 2;

        ArrayList<Item> listGenerated = itemDAO.createItems(bookID, shelfID, quantity);

        Assert.assertNotNull(listGenerated);
        Assert.assertEquals(2, listGenerated.size());
        Assert.assertEquals("Ready", listGenerated.get(0).getStatus());
        Assert.assertTrue(listGenerated.get(0).getBarcode().startsWith("BC-ITM"));
    }

    // Test trường hợp 4: Truyền vào số lượng bằng 0
    @Test
    public void testCreateItems_Fail_ZeroQuantity() {
        ItemDAO itemDAO = new ItemDAO();
        String bookID = "B999";
        String shelfID = "S999";
        int quantity = 0;

        ArrayList<Item> listGenerated = itemDAO.createItems(bookID, shelfID, quantity);

        Assert.assertNotNull(listGenerated);
        Assert.assertEquals(0, listGenerated.size());
    }
}
