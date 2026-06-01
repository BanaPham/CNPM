package test.unit;

import java.sql.Connection;
import java.sql.Statement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dao.DAO;
import dao.PatronDAO;
import model.Patron;

public class PatronDaoTest {
    private PatronDAO patronDAO = new PatronDAO();
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
            stmt.execute("DELETE FROM tblpatron WHERE id = 1");
            stmt.execute("INSERT INTO tblpatron(id, card_number, full_name, outstanding_debt) VALUES(1, 'DG001', 'Nguyen Van A', 15000.0)");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (con != null) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                stmt.execute("DELETE FROM tblpatron WHERE id = 1");
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
        }
    }

    @Test
    public void testSearchPatronStandard() {
        String cardNum = "DG001";
        Patron patron = patronDAO.searchPatron(cardNum);
        Assert.assertNotNull(patron);
        Assert.assertEquals("DG001", patron.getCardNumber());
        Assert.assertEquals("Nguyen Van A", patron.getFullName());
        Assert.assertEquals(15000.0, patron.getOutstandingDebt(), 0.001);
    }

    @Test
    public void testSearchPatronException() {
        String cardNum = "DG999";
        Patron patron = patronDAO.searchPatron(cardNum);
        Assert.assertNull(patron);
    }
}
