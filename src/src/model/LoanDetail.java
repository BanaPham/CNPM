package model;

import java.io.Serializable;
import java.sql.Date;

public class LoanDetail implements Serializable {
    private String tblLoanRecordID;
    private String tblItemID;
    private Date returnDate;
    private String conditionNote;

    public LoanDetail() {
    }

    public LoanDetail(String tblLoanRecordID, String tblItemID, Date returnDate, String conditionNote) {
        this.tblLoanRecordID = tblLoanRecordID;
        this.tblItemID = tblItemID;
        this.returnDate = returnDate;
        this.conditionNote = conditionNote;
    }

    public String getTblLoanRecordID() { return tblLoanRecordID; }
    public void setTblLoanRecordID(String tblLoanRecordID) { this.tblLoanRecordID = tblLoanRecordID; }

    public String getTblItemID() { return tblItemID; }
    public void setTblItemID(String tblItemID) { this.tblItemID = tblItemID; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    public String getConditionNote() { return conditionNote; }
    public void setConditionNote(String conditionNote) { this.conditionNote = conditionNote; }
}
