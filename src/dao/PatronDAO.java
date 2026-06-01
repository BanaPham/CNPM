package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Patron;

public class PatronDAO extends DAO {

    public PatronDAO() {
        super();
    }

    // 1. Hàm kiểm tra trùng lặp CCCD
    public boolean checkDuplicateCCCD(String cccd) {
        boolean isDuplicate = false;
        String sql = "SELECT * FROM tblPatron WHERE cccd = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                isDuplicate = true; // Tìm thấy -> Đã trùng
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    // 2. Hàm thêm mới Độc giả vào CSDL
    public boolean createPatron(Patron p) {
        boolean isSuccess = false;
        String sql = "INSERT INTO tblPatron (patronID, fullName, dob, cccd, phone, email, address, avatar, cardType, expiryDate, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                   
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getPatronID());
            ps.setString(2, p.getFullName());
            
            // Xử lý ép kiểu Ngày tháng (java.util.Date -> java.sql.Date)
            if (p.getDob() != null) {
                ps.setDate(3, new java.sql.Date(p.getDob().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            ps.setString(4, p.getCccd());
            ps.setString(5, p.getPhone());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getAddress());
            ps.setString(8, p.getAvatar());
            ps.setString(9, p.getCardType());
            
            if (p.getExpiryDate() != null) {
                ps.setDate(10, new java.sql.Date(p.getExpiryDate().getTime()));
            } else {
                ps.setNull(10, java.sql.Types.DATE);
            }
            
            ps.setString(11, p.getStatus());
            
            // Dùng executeUpdate() cho lệnh INSERT, UPDATE, DELETE
            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}