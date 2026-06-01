package test.unit;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import dao.DAO;
import dao.LoanRecordDAO;
import model.LoanRecord;
import model.LoanDetail;
import model.Item;

public class ReturnLoanRecordDaoTest {
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
            stmt.execute("DELETE FROM tblloandetail WHERE id = 1");
            stmt.execute("DELETE FROM tblloanrecord WHERE id = 1");
            stmt.execute("DELETE FROM tblitem WHERE id = 1");
            stmt.execute("DELETE FROM tblbook WHERE id = 1");
            stmt.execute("DELETE FROM tblpatron WHERE id = 1");
            
            stmt.execute("INSERT INTO tblpatron(id, card_number, full_name, outstanding_debt) VALUES(1, 'DG001', 'Nguyen Van A', 15000.0)");
            stmt.execute("INSERT INTO tblbook(id, title, price) VALUES(1, 'Lap trinh Java Swing', 50000.0)");
            stmt.execute("INSERT INTO tblitem(id, barcode, status, book_id) VALUES(1, 'BC001', 'Borrowed', 1)");
            stmt.execute("INSERT INTO tblloanrecord(id, loan_date, due_date, patron_id, fine_amount) VALUES(1, '2026-05-10 08:00:00', '2026-05-24 08:00:00', 1, 0.0)");
            stmt.execute("INSERT INTO tblloandetail(id, loan_record_id, item_id, actual_return_date, fine_amount, status_notes) VALUES(1, 1, 1, NULL, 0.0, 'Muon moi')");
            
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (con != null) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                stmt.execute("DELETE FROM tblloandetail WHERE id = 1");
                stmt.execute("DELETE FROM tblloanrecord WHERE id = 1");
                stmt.execute("DELETE FROM tblitem WHERE id = 1");
                stmt.execute("DELETE FROM tblbook WHERE id = 1");
                stmt.execute("DELETE FROM tblpatron WHERE id = 1");
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
        }
    }

    @Test
    public void testGetActiveLoanDetailStandard() {
        Item item = new Item();
        item.setId(1);
        item.setBarcode("BC001");
        
        LoanDetail detail = loanRecordDAO.getActiveLoanDetail(item);
        Assert.assertNotNull(detail);
        Assert.assertEquals(1, detail.getLoanRecord().getId());
        Assert.assertNull(detail.getReturnDate());
        Assert.assertEquals("Nguyen Van A", detail.getLoanRecord().getPatron().getFullName());
    }

    @Test
    public void testGetActiveLoanDetailException() {
        Item item = new Item();
        item.setId(999);
        item.setBarcode("BC999");
        
        LoanDetail detail = loanRecordDAO.getActiveLoanDetail(item);
        Assert.assertNull(detail);
    }

    @Test
    public void testReturnLoanDetailStandard() {
        LoanDetail detail = new LoanDetail();
        detail.setId(1);
        detail.setReturnDate(new Date());
        
        boolean result = loanRecordDAO.returnLoanDetail(detail);
        Assert.assertTrue(result);
        
        // Khôi phục lại ngày trả cũ là null để tránh làm hỏng dữ liệu test
        detail.setReturnDate(null);
        loanRecordDAO.returnLoanDetail(detail);
    }

    @Test
    public void testUpdateFineStandard() {
        LoanRecord record = new LoanRecord();
        record.setId(1);
        
        boolean result = loanRecordDAO.updateFine(record, 10000.0);
        Assert.assertTrue(result);
        Assert.assertEquals(10000.0, record.getFineAmount(), 0.001);
        
        // Reset lại tiền phạt cũ bằng 0.0
        loanRecordDAO.updateFine(record, 0.0);
    }
}
