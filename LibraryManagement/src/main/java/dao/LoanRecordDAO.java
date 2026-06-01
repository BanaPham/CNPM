package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import model.LoanRecord;
import model.LoanDetail;
import model.Patron;
import model.Item;

public class LoanRecordDAO extends DAO {
    public LoanRecordDAO() {
        super();
    }

    // Method used by MuonSach (Borrow)
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

    // Method used by TraSach (Return)
    public LoanDetail getActiveLoanDetail(Item item) {
        LoanDetail detail = null;
        String sql = "SELECT ld.id as detail_id, ld.actual_return_date as actualReturnDate, ld.fine_amount as fineAmount, ld.status_notes as statusNotes, " +
                     "lr.id as record_id, lr.loan_date as borrowDate, ld.due_date as dueDate, p.id as patron_id, p.full_name as fullName, p.card_number as cardNumber " +
                     "FROM tblloandetail ld " +
                     "INNER JOIN tblloanrecord lr ON ld.loan_record_id = lr.id " +
                     "INNER JOIN tblpatron p ON lr.patron_id = p.id " +
                     "WHERE ld.item_id = ? AND ld.actual_return_date IS NULL";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, item.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patron patron = new Patron();
                patron.setId(rs.getInt("patron_id"));
                patron.setFullName(rs.getString("fullName"));
                patron.setCardNumber(rs.getString("cardNumber"));

                LoanRecord record = new LoanRecord();
                record.setId(rs.getInt("record_id"));
                record.setBorrowDate(rs.getTimestamp("borrowDate"));
                record.setDueDate(rs.getTimestamp("dueDate"));
                record.setPatron(patron);

                detail = new LoanDetail();
                detail.setId(rs.getInt("detail_id"));
                detail.setItem(item);
                detail.setReturnDate(rs.getTimestamp("actualReturnDate"));
                detail.setLoanRecord(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    // Method used by TraSach (Return)
    public boolean returnLoanDetail(LoanDetail detail) {
        boolean result = false;
        String sql = "UPDATE tblloandetail SET actual_return_date = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            if (detail.getReturnDate() != null) {
                ps.setTimestamp(1, new Timestamp(detail.getReturnDate().getTime()));
            } else {
                ps.setNull(1, java.sql.Types.TIMESTAMP);
            }
            ps.setInt(2, detail.getId());
            int count = ps.executeUpdate();
            if (count > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method used by TraSach (Return)
    public boolean updateFine(LoanRecord record, double amount) {
        boolean result = false;
        String sql = "UPDATE tblloanrecord SET fine_amount = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, record.getId());
            int count = ps.executeUpdate();
            if (count > 0) {
                record.setFineAmount(amount);
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
