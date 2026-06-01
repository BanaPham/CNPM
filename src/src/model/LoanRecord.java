package model;

import java.io.Serializable;
import java.sql.Date;

public class LoanRecord implements Serializable {
    private String loanID;
    private Date loanDate;
    private Date dueDate;
    private double fineAmount;
    private String tblSystemUserID;
    private String tblPatronID;

    public LoanRecord() {
    }

    public LoanRecord(String loanID, Date loanDate, Date dueDate, double fineAmount, String tblSystemUserID,
            String tblPatronID) {
        this.loanID = loanID;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.fineAmount = fineAmount;
        this.tblSystemUserID = tblSystemUserID;
        this.tblPatronID = tblPatronID;
    }

    public String getLoanID() { return loanID; }
    public void setLoanID(String loanID) { this.loanID = loanID; }

    public Date getLoanDate() { return loanDate; }
    public void setLoanDate(Date loanDate) { this.loanDate = loanDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    public String getTblSystemUserID() { return tblSystemUserID; }
    public void setTblSystemUserID(String tblSystemUserID) { this.tblSystemUserID = tblSystemUserID; }

    public String getTblPatronID() { return tblPatronID; }
    public void setTblPatronID(String tblPatronID) { this.tblPatronID = tblPatronID; }
}
