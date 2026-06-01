package test.unit;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dao.DAO;
import dao.LoanRecordDAO;
import dao.ItemDAO;
import model.LoanRecord;
import model.LoanDetail;
import model.Patron;
import model.SystemUser;
import model.Item;

public class BorrowLoanRecordDaoTest {
    private LoanRecordDAO loanRecordDAO = new LoanRecordDAO();
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
            
            // Safe clean up: only delete test records associated with our mock ids
            stmt.execute("DELETE FROM tblloandetail WHERE item_id = 999");
            stmt.execute("DELETE FROM tblloanrecord WHERE patron_id = 999 OR librarian_id = 993");
            stmt.execute("DELETE FROM tblitem WHERE id = 999");
            stmt.execute("DELETE FROM tblbook WHERE id = 999");
            stmt.execute("DELETE FROM tblpatron WHERE id = 999");
            stmt.execute("DELETE FROM tblsystemuser WHERE id = 993");
            
            stmt.execute("INSERT INTO tblpatron(id, card_number, full_name, outstanding_debt) VALUES(999, 'DG_TEST_001', 'Nguyen Van A', 15000.0)");
            stmt.execute("INSERT INTO tblsystemuser(id, username, password, name, role) VALUES(993, 'librarian1', '123456', 'Nguyen Van D', 'Librarian')");
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
                stmt.execute("DELETE FROM tblloandetail WHERE item_id = 999");
                stmt.execute("DELETE FROM tblloanrecord WHERE patron_id = 999 OR librarian_id = 993");
                stmt.execute("DELETE FROM tblitem WHERE id = 999");
                stmt.execute("DELETE FROM tblbook WHERE id = 999");
                stmt.execute("DELETE FROM tblpatron WHERE id = 999");
                stmt.execute("DELETE FROM tblsystemuser WHERE id = 993");
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
        }
    }

    @Test
    public void testAddLoanRecordStandard() {
        Patron patron = new Patron();
        patron.setId(999);
        
        SystemUser librarian = new SystemUser();
        librarian.setId(993);
        
        LoanRecord loan = new LoanRecord();
        loan.setPatron(patron);
        loan.setLibrarian(librarian);
        loan.setBorrowDate(new Date());
        loan.setDueDate(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000));
        
        Item item = new Item();
        item.setId(999);
        item.setBarcode("BC_TEST_001");
        
        LoanDetail detail = new LoanDetail();
        detail.setItem(item);
        detail.setStatusNotes("Muon moi");
        
        loan.getLoanDetails().add(detail);
        
        try {
            boolean result = loanRecordDAO.addLoanRecord(loan);
            Assert.assertTrue(result);
            Assert.assertTrue(loan.getId() > 0);
            
            // Kiem tra trang thai ban sao sach duoc cap nhat thanh "Borrowed"
            ItemDAO itemDAO = new ItemDAO();
            Item updatedItem = itemDAO.searchItem("BC_TEST_001");
            Assert.assertNotNull(updatedItem);
            Assert.assertEquals("Borrowed", updatedItem.getStatus());
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
