package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import model.LoanRecord;
import model.LoanDetail;

public class LoanRecordDAO extends DAO {
    public LoanRecordDAO() {
        super();
    }

    public boolean addLoanRecord(LoanRecord loan) {
        boolean result = false;
        String sqlLoan = "INSERT INTO tblloanrecord(loan_date, patron_id, librarian_id) VALUES(?, ?, ?)";
        String sqlDetail = "INSERT INTO tblloandetail(due_date, item_id, loan_record_id) VALUES(?, ?, ?)";
        String sqlUpdateItem = "UPDATE tblitem SET status = ? WHERE id = ?";

        try {
            con.setAutoCommit(false);

            PreparedStatement psLoan = con.prepareStatement(sqlLoan, Statement.RETURN_GENERATED_KEYS);
            psLoan.setTimestamp(1, new Timestamp(loan.getBorrowDate().getTime()));
            psLoan.setInt(2, loan.getPatron().getId());
            psLoan.setInt(3, loan.getLibrarian().getId());

            psLoan.executeUpdate();
            ResultSet rs = psLoan.getGeneratedKeys();
            int loanRecordId = 0;
            if (rs.next()) {
                loanRecordId = rs.getInt(1);
                loan.setId(loanRecordId);
            }

            PreparedStatement psDetail = con.prepareStatement(sqlDetail);
            PreparedStatement psUpdateItem = con.prepareStatement(sqlUpdateItem);

            for (LoanDetail detail : loan.getLoanDetails()) {
                psDetail.setTimestamp(1, new Timestamp(loan.getDueDate().getTime()));
                psDetail.setInt(2, detail.getItem().getId());
                psDetail.setInt(3, loanRecordId);
                psDetail.executeUpdate();

                psUpdateItem.setString(1, "Borrowed");
                psUpdateItem.setInt(2, detail.getItem().getId());
                psUpdateItem.executeUpdate();
            }

            con.commit();
            result = true;
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}