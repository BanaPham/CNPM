package test.unit;

import dao.BookDAO;
import dao.DAO;
import org.junit.Assert;
import org.junit.After;
import org.junit.Test;
import model.Book;

import java.sql.Connection;
import java.sql.Statement;

public class BookDaoTest {

    @After
    public void tearDown() throws Exception {
        Connection con = DAO.con;
        if (con != null) {
            try (Statement st = con.createStatement()) {
                st.executeUpdate("DELETE FROM tblbook WHERE isbn = '978-0596009205'");
            }
        }
    }

    // Test trường hợp 1: Dữ liệu sách đầu vào hợp lệ
    @Test
    public void testAddBook_Success() {
        BookDAO bookDAO = new BookDAO();
        Book validBook = new Book();
        validBook.setTitle("Head First Java");
        validBook.setAuthor("Kathy Sierra");
        validBook.setIsbn("978-0596009205");
        validBook.setPublisher("O'Reilly");
        validBook.setPublishYear(2003);
        validBook.setDdcCode("005.133");
        validBook.setPrice(35.5);

        String generatedBookID = bookDAO.addBook(validBook);

        Assert.assertNotNull(generatedBookID);
        Assert.assertTrue(generatedBookID.startsWith("B"));
    }

    // Test trường hợp 2: Dữ liệu đầu vào rỗng (null)
    @Test
    public void testAddBook_Fail_NullObject() {
        BookDAO bookDAO = new BookDAO();
        Book nullBook = null;

        String result = bookDAO.addBook(nullBook);

        Assert.assertNull(result);
    }
}
