package dao;
import model.Patron;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatronDAO extends DAO {
    public PatronDAO() {
        super();
    }

    // CHECKDUPLICATECCCD
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

    // CREATE PATRON
    public boolean createPatron(Patron p) {
        // Tự động sinh username và password nếu để trống
        if (p.getUsername() == null || p.getUsername().trim().isEmpty()) {
            p.setUsername(p.getPatronID()); // Sử dụng mã thẻ làm tên đăng nhập mặc định
        }
        if (p.getPassword() == null || p.getPassword().trim().isEmpty()) {
            p.setPassword("123456"); // Mật khẩu mặc định
        }

        boolean isSuccess = false;
        String sql = "INSERT INTO tblPatron (patronID, fullName, dob, cccd, phone, email, address, avatar, cardType, expiryDate, status, username, password) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            ps.setString(12, p.getUsername());
            ps.setString(13, p.getPassword());

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

    // LOGIN
    public boolean checkLogin(Patron patron) {
        boolean result = false;
        try {
            String sql =
                    "SELECT * FROM tblPatron "
                            + "WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patron.getUsername());
            ps.setString(2, patron.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patron.setPatronID(rs.getString("patronID"));
                patron.setFullName(rs.getString("fullName"));
                patron.setPhone(rs.getString("phone"));
                patron.setAddress(rs.getString("address"));
                patron.setEmail(rs.getString("email"));
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // UPDATE PROFILE
    public boolean updateProfile(Patron p) {
        if (p.getPhone() == null || p.getPhone().trim().isEmpty()) {
            return false;
        }
        if (p.getAddress() == null || p.getAddress().trim().isEmpty()) {
            return false;
        }
        boolean result = false;
        try {
            String sql =
                    "UPDATE tblPatron "
                            + "SET password=?, phone=?, address=? "
                            + "WHERE patronID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getPassword());
            ps.setString(2, p.getPhone());
            ps.setString(3, p.getAddress());
            ps.setString(4, p.getPatronID());
            int row = ps.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // GET PROFILE
    public Patron getProfile(String patronID) {
        Patron p = null;
        try {
            String sql = "SELECT * FROM tblPatron WHERE patronID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patronID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Patron();
                p.setPatronID(rs.getString("patronID"));
                p.setUsername(rs.getString("username"));
                p.setFullName(rs.getString("fullName"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                p.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    // SEARCH CARD
    public Patron[] searchCard(Patron p) {
        List<Patron> list = new ArrayList<>();
        // Tìm kiếm theo tên hoặc mã dựa trên thuộc tính fullName truyền từ txtKey
        String sql = "SELECT * FROM tblPatron WHERE fullName LIKE ? OR patronID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            String keyword = p.getFullName();
            if (keyword == null) {
                keyword = "";
            }

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, keyword);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Patron patron = new Patron();
                patron.setPatronID(rs.getString("patronID"));
                patron.setFullName(rs.getString("fullName"));
                patron.setStatus(rs.getString("status"));
                patron.setCardType(rs.getString("cardType"));
                patron.setExpiryDate(rs.getDate("expiryDate"));
                // Set thêm các thuộc tính khác nếu cần...
                list.add(patron);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list.isEmpty()) return null;
        return list.toArray(new Patron[0]);
    }

    // UPDATE STATUS
    public boolean updateStatus(Patron p) {
        try {
            String sql;
            if (p.getExpiryDate() == null) {
                sql = "UPDATE tblPatron SET status = ? WHERE patronID = ?";
            } else {
                sql = "UPDATE tblPatron SET status = ?, expiryDate = ? WHERE patronID = ?";
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getStatus());

            if (p.getExpiryDate() == null) {
                ps.setString(2, p.getPatronID());
            } else {
                ps.setDate(2, new java.sql.Date(p.getExpiryDate().getTime()));
                ps.setString(3, p.getPatronID());
            }

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}