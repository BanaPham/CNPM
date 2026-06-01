package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Book;

public class BookDAO extends DAO {
    
    public BookDAO() {
        super(); // Gọi constructor của lớp cha để đảm bảo có kết nối 'con'
    }

    public ArrayList<Book> searchBook(String key) {
        ArrayList<Book> result = new ArrayList<>();
        // Tìm theo Tiêu đề hoặc Tác giả
        String sql = "SELECT * FROM tblBook WHERE title LIKE ? OR author LIKE ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            
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
                
                result.add(book); // Thêm vào danh sách trả về
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}