package test;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import dao.BookDAO;
import model.Book;

public class BookDaoTest {

    // Test trường hợp 1: Tìm với từ khóa CÓ TỒN TẠI ("Clean")
    @Test
    public void testSearchBook_Found() {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> listBook = bookDAO.searchBook("Clean");
        
        Assert.assertNotNull(listBook); // Đảm bảo danh sách không bị null
        Assert.assertEquals(1, listBook.size()); // DB mẫu chỉ có 1 cuốn chứa từ "Clean"
        Assert.assertEquals("Clean Code", listBook.get(0).getTitle());
    }

    // Test trường hợp 2: Tìm với từ khóa KHÔNG TỒN TẠI ("NonExistentBook123")
    @Test
    public void testSearchBook_NotFound() {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> listBook = bookDAO.searchBook("NonExistentBook123");
        
        Assert.assertNotNull(listBook);
        Assert.assertEquals(0, listBook.size()); // Đảm bảo trả về danh sách rỗng (size = 0)
    }
}