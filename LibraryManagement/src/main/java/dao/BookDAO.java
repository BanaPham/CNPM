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
}
