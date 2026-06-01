package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Patron;

public class PatronDAO extends DAO {
    public PatronDAO() {
        super();
    }

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
                patron.setOutstandingDebt(rs.getFloat("outstanding_debt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patron;
    }
}