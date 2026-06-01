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

        // SQL theo đúng schema tblBook trong db_library (không có cột author, publisher)
        String sql = "INSERT INTO tblBook (bookID, title, isbn, publishYear, deDCcode, price, summary) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (book == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, generatedBookID);
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getPublishYear());
            ps.setString(5, book.getDdcCode());
            ps.setDouble(6, book.getPrice());
            ps.setString(7, book.getSummary() != null ? book.getSummary() : "");

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return generatedBookID; // Thành công trả về mã sách
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Thất bại
    }
}
