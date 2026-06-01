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
 
    	String sqlActivePatron = "SELECT COUNT(*) FROM tblPatron WHERE expiryDate >= CURRENT_DATE()";
    	String sqlLoan = "SELECT COUNT(*) FROM tblLoanRecord WHERE loanDate >= ? AND loanDate <= ?";
    	String sqlOverdue = "SELECT COUNT(*) FROM tblLoanDetail ld, tblLoanRecord lr "
            	+ "WHERE ld.tblLoanRecordID = lr.loanID AND ld.returnDate IS NULL AND lr.dueDate < CURRENT_DATE() "
            	+ "AND lr.loanDate >= ? AND lr.loanDate <= ?";
    	String sqlFine = "SELECT IFNULL(SUM(fineAmount), 0) FROM tblLoanRecord WHERE loanDate >= ? AND loanDate <= ?";
    	String sqlBook = "SELECT COUNT(*) FROM tblBook";
    	String sqlBorrowingItem = "SELECT COUNT(*) FROM tblLoanDetail WHERE returnDate IS NULL";
    	String sqlReservation = "SELECT COUNT(*) FROM tblReservation WHERE resDate >= ? AND resDate <= ?";
 
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
    	String sql = "SELECT lr.loanID, lr.loanDate, lr.dueDate, lr.fineAmount, "
            	+ "su.fullName AS librarianName, p.name AS patronName, "
            	+ "ld.tblItemID, ld.returnDate, ld.conditionNote, "
            	+ "i.barCode, i.status AS itemStatus, b.title, s.room, s.row "
            	+ "FROM tblLoanRecord lr, tblSystemUser su, tblPatron p, tblLoanDetail ld, tblItem i, tblBook b, tblShelf s "
            	+ "WHERE lr.tblSystemUserID = su.userID AND lr.tblPatronID = p.patronID "
            	+ "AND ld.tblLoanRecordID = lr.loanID AND ld.tblItemID = i.itemID "
            	+ "AND i.tblBookID = b.bookID AND i.tblShelfID = s.shelfID "
            	+ "AND lr.loanDate >= ? AND lr.loanDate <= ? "
            	+ "ORDER BY lr.loanDate DESC, lr.loanID, ld.tblItemID";
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
