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
        // Select from tblbook (MySQL lowercase)
        String sql = "SELECT * FROM tblbook WHERE title LIKE ? OR author LIKE ?";

        try {
            if (con == null || con.isClosed()) {
                System.err.println("Database connection is null or closed!");
                return result;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            String searchKey = "%" + key + "%";
            ps.setString(1, searchKey);
            ps.setString(2, searchKey);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                // Map MySQL columns to model fields
                book.setBookID(String.valueOf(rs.getInt("id")));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishYear(rs.getInt("publish_year"));
                book.setDdcCode(rs.getString("ddc_code"));
                book.setCoverImage(rs.getString("cover_image"));
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

    public String addBook(Book book) {
        if (book == null) {
            return null;
        }
        String sql = "INSERT INTO tblbook (title, author, isbn, publisher, publish_year, ddc_code, price, cover_image, summary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getPublisher());
            ps.setInt(5, book.getPublishYear());
            ps.setString(6, book.getDdcCode());
            ps.setDouble(7, book.getPrice());
            ps.setString(8, book.getCoverImage());
            ps.setString(9, book.getSummary());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    book.setId(id);
                    return "B" + id;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
