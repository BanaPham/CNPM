package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Patron;

public class PatronDAO extends DAO {
    public PatronDAO() {
        super();
    }

    // Method used by MuonSach/TraSach (Borrow/Return)
    public Patron searchPatron(String cardNum) {
        Patron patron = null;
        String sql = "SELECT * FROM tblpatron WHERE card_number = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cardNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patron = new Patron();
                patron.setId(rs.getInt("id"));
                patron.setCardNumber(rs.getString("card_number"));
                patron.setFullName(rs.getString("full_name"));
                patron.setOutstandingDebt(rs.getDouble("outstanding_debt"));
                // Fetch other profile properties too so they are populated
                patron.setDob(rs.getDate("dob"));
                patron.setCccd(rs.getString("cccd"));
                patron.setPhone(rs.getString("phone"));
                patron.setEmail(rs.getString("email"));
                patron.setAddress(rs.getString("address"));
                patron.setAvatar(rs.getString("avatar"));
                patron.setCardType(rs.getString("card_type"));
                patron.setExpiryDate(rs.getDate("expiry_date"));
                patron.setStatus(rs.getString("status"));
                patron.setUsername(rs.getString("username"));
                patron.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patron;
    }

    // Method used by Patron Registration: CHECKDUPLICATECCCD (from root src)
    public boolean checkDuplicateCCCD(String cccd) {
        boolean isDuplicate = false;
        String sql = "SELECT * FROM tblpatron WHERE cccd = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isDuplicate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    // Method used by Patron Registration: CREATE PATRON (from root src)
    public boolean createPatron(Patron p) {
        if (p.getUsername() == null || p.getUsername().trim().isEmpty()) {
            p.setUsername(p.getPatronID());
        }
        if (p.getPassword() == null || p.getPassword().trim().isEmpty()) {
            p.setPassword("123456");
        }

        boolean isSuccess = false;
        String sql = "INSERT INTO tblpatron (card_number, full_name, dob, cccd, phone, email, address, avatar, card_type, expiry_date, status, username, password) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getPatronID());
            ps.setString(2, p.getFullName());

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

            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    // Method used by Patron Login: LOGIN (from root src)
    public boolean checkLogin(Patron patron) {
        boolean result = false;
        try {
            String sql = "SELECT * FROM tblpatron WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patron.getUsername());
            ps.setString(2, patron.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patron.setId(rs.getInt("id"));
                patron.setPatronID(rs.getString("card_number"));
                patron.setFullName(rs.getString("full_name"));
                patron.setPhone(rs.getString("phone"));
                patron.setAddress(rs.getString("address"));
                patron.setEmail(rs.getString("email"));
                patron.setOutstandingDebt(rs.getDouble("outstanding_debt"));
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method used by Patron Profile Update: UPDATE PROFILE (from root src)
    public boolean updateProfile(Patron p) {
        if (p.getPhone() == null || p.getPhone().trim().isEmpty()) {
            return false;
        }
        if (p.getAddress() == null || p.getAddress().trim().isEmpty()) {
            return false;
        }
        boolean result = false;
        try {
            String sql = "UPDATE tblpatron SET password=?, phone=?, address=? WHERE card_number=?";
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

    // Method used by Patron Profile Get: GET PROFILE (from root src)
    public Patron getProfile(String patronID) {
        Patron p = null;
        try {
            String sql = "SELECT * FROM tblpatron WHERE card_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patronID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Patron();
                p.setId(rs.getInt("id"));
                p.setPatronID(rs.getString("card_number"));
                p.setUsername(rs.getString("username"));
                p.setFullName(rs.getString("full_name"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                p.setEmail(rs.getString("email"));
                p.setOutstandingDebt(rs.getDouble("outstanding_debt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    // Method used by Librarian System Cards Search: SEARCH CARD (from root src)
    public Patron[] searchCard(Patron p) {
        List<Patron> list = new ArrayList<>();
        String sql = "SELECT * FROM tblpatron WHERE full_name LIKE ? OR card_number = ?";
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
                patron.setId(rs.getInt("id"));
                patron.setPatronID(rs.getString("card_number"));
                patron.setFullName(rs.getString("full_name"));
                patron.setStatus(rs.getString("status"));
                patron.setCardType(rs.getString("card_type"));
                patron.setExpiryDate(rs.getDate("expiry_date"));
                patron.setOutstandingDebt(rs.getDouble("outstanding_debt"));
                list.add(patron);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list.isEmpty()) return null;
        return list.toArray(new Patron[0]);
    }

    // Method used by Librarian System Cards Update Status: UPDATE STATUS (from root src)
    public boolean updateStatus(Patron p) {
        try {
            String sql;
            if (p.getExpiryDate() == null) {
                sql = "UPDATE tblpatron SET status = ? WHERE card_number = ?";
            } else {
                sql = "UPDATE tblpatron SET status = ?, expiry_date = ? WHERE card_number = ?";
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
