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

public class BorrowItemDaoTest {
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
            stmt.execute("DELETE FROM tblitem WHERE id = 999");
            stmt.execute("DELETE FROM tblbook WHERE id = 999");
            stmt.execute("INSERT INTO tblbook(id, title, isbn, author) VALUES(999, 'Lap trinh Java Swing', 'ISBN_TEST_001', 'Nguyen Van C')");
            stmt.execute("INSERT INTO tblitem(id, barcode, status, price, book_id) VALUES(999, 'BC_TEST_001', 'Ready', 50000.0, 999)");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (con != null) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                stmt.execute("DELETE FROM tblitem WHERE id = 999");
                stmt.execute("DELETE FROM tblbook WHERE id = 999");
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
        }
    }

    @Test
    public void testSearchItemStandard() {
        String barcode = "BC_TEST_001";
        Item item = itemDAO.searchItem(barcode);
        Assert.assertNotNull(item);
        Assert.assertEquals("BC_TEST_001", item.getBarcode());
        Assert.assertEquals("Ready", item.getStatus());
        Assert.assertNotNull(item.getBook());
        Assert.assertEquals("Lap trinh Java Swing", item.getBook().getTitle());
    }

    @Test
    public void testSearchItemException() {
        String barcode = "BC999";
        Item item = itemDAO.searchItem(barcode);
        Assert.assertNull(item);
    }
}
