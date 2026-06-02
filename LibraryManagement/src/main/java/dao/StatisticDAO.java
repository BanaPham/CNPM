package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StatisticDAO extends DAO {
    public StatisticDAO() {
        super();
    }

    private int getIntValue(String sql, Date startDate, Date endDate) {
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            if (startDate != null && endDate != null) {
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private double getDoubleValue(String sql, Date startDate, Date endDate) {
        double result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            if (startDate != null && endDate != null) {
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String[]> getStatistic(Date startDate, Date endDate) {
        ArrayList<String[]> result = new ArrayList<String[]>();

        String sqlActivePatron = "SELECT COUNT(*) FROM tblpatron WHERE expiry_date >= CURRENT_DATE()";
        String sqlLoan = "SELECT COUNT(*) FROM tblloanrecord WHERE loan_date >= ? AND loan_date <= ?";
        String sqlOverdue = "SELECT COUNT(*) FROM tblloandetail ld, tblloanrecord lr "
                + "WHERE ld.loan_record_id = lr.id AND ld.actual_return_date IS NULL AND lr.due_date < CURRENT_DATE() "
                + "AND lr.loan_date >= ? AND lr.loan_date <= ?";
        String sqlFine = "SELECT IFNULL(SUM(fine_amount), 0) FROM tblloanrecord WHERE loan_date >= ? AND loan_date <= ?";
        String sqlBook = "SELECT COUNT(*) FROM tblbook";
        String sqlBorrowingItem = "SELECT COUNT(*) FROM tblloandetail WHERE actual_return_date IS NULL";
        String sqlReservation = "SELECT COUNT(*) FROM tblreservation WHERE res_date >= ? AND res_date <= ?";

        result.add(new String[] { "Tổng số thẻ đang hoạt động", String.valueOf(getIntValue(sqlActivePatron, null, null)) });
        result.add(new String[] { "Số lượt mượn trong khoảng thời gian", String.valueOf(getIntValue(sqlLoan, startDate, endDate)) });
        result.add(new String[] { "Số sách quá hạn chưa trả", String.valueOf(getIntValue(sqlOverdue, startDate, endDate)) });
        result.add(new String[] { "Doanh thu phí phạt", String.format("%.0f", getDoubleValue(sqlFine, startDate, endDate)) });
        result.add(new String[] { "Tổng số đầu sách", String.valueOf(getIntValue(sqlBook, null, null)) });
        result.add(new String[] { "Số bản sao đang mượn", String.valueOf(getIntValue(sqlBorrowingItem, null, null)) });
        result.add(new String[] { "Số lượt đặt trước", String.valueOf(getIntValue(sqlReservation, startDate, endDate)) });
        return result;
    }

    public ArrayList<String[]> getLoanDetailReport(Date startDate, Date endDate) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        String sql = "SELECT lr.id AS loanID, lr.loan_date AS loanDate, lr.due_date AS dueDate, lr.fine_amount AS fineAmount, "
                + "su.name AS librarianName, p.full_name AS patronName, "
                + "ld.item_id AS tblItemID, ld.actual_return_date AS returnDate, ld.status_notes AS conditionNote, "
                + "i.barcode AS barCode, i.status AS itemStatus, b.title, s.room, s.`row` "
                + "FROM tblloanrecord lr, tblsystemuser su, tblpatron p, tblloandetail ld, tblitem i, tblbook b, tblshelf s "
                + "WHERE lr.librarian_id = su.id AND lr.patron_id = p.id "
                + "AND ld.loan_record_id = lr.id AND ld.item_id = i.id "
                + "AND i.book_id = b.id AND i.shelf_id = s.id "
                + "AND lr.loan_date >= ? AND lr.loan_date <= ? "
                + "ORDER BY lr.loan_date DESC, lr.id, ld.item_id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new String[] { rs.getString("loanID"), String.valueOf(rs.getDate("loanDate")),
                        String.valueOf(rs.getDate("dueDate")), rs.getString("patronName"),
                        rs.getString("librarianName"), rs.getString("tblItemID"), rs.getString("barCode"),
                        rs.getString("title"), rs.getString("room") + " - " + rs.getString("row"),
                        rs.getDate("returnDate") == null ? "Chưa trả" : String.valueOf(rs.getDate("returnDate")),
                        rs.getString("conditionNote"), String.format("%.0f", rs.getDouble("fineAmount")) });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
