package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Book;

public class BookDAO extends DAO {

    public BookDAO() {
        super();
    }

    public ArrayList<Book> searchBook(String key) {
        ArrayList<Book> result = new ArrayList<>();
        // Use N prefix for Unicode search in SQL Server if needed, 
        // but PreparedStatement ? with setNString is the standard way.
        String sql = "SELECT * FROM tblBook WHERE title LIKE ? OR author LIKE ?";

        try {
            if (con == null || con.isClosed()) {
                System.err.println("Database connection is null or closed!");
                return result;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            String searchKey = "%" + key + "%";
            ps.setNString(1, searchKey);
            ps.setNString(2, searchKey);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getString("bookID"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishYear(rs.getInt("publishYear"));
                book.setDdcCode(rs.getString("ddcCode"));
                book.setCoverImage(rs.getString("coverImage"));
                book.setSummary(rs.getString("summary"));
                book.setPrice(rs.getDouble("price"));

                result.add(book);
            }
            System.out.println("Search keyword: " + key + " - Found: " + result.size() + " books.");
        } catch (Exception e) {
            System.err.println("Error searching book: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
