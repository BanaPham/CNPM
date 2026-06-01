package dao;

import model.Book;

import java.sql.PreparedStatement;

public class BookDAO extends DAO {

    public BookDAO() {
        super();
    }

    public String addBook(Book book) {
        // Sinh mã sách ngẫu nhiên dựa trên thời gian
        String generatedBookID = "B" + System.currentTimeMillis();

        String sql = "INSERT INTO tblBook (bookID, title, author, isbn, publisher, publishYear, ddcCode, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        if (book == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, generatedBookID);
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getPublisher());
            ps.setInt(6, book.getPublishYear());
            ps.setString(7, book.getDdcCode());
            ps.setDouble(8, book.getPrice());

            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0) {
                return generatedBookID; // Thành công trả về mã sách
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Thất bại
    }
}
