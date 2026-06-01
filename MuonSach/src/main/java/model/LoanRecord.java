package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class LoanRecord implements Serializable {
    private int id;
    private Date borrowDate;
    private Date dueDate;
    private Patron patron;
    private SystemUser librarian;
    private List<LoanDetail> loanDetails;

    public LoanRecord() {
        super();
        this.loanDetails = new ArrayList<LoanDetail>();
    }

    public LoanRecord(Date borrowDate, Date dueDate, Patron patron, SystemUser librarian) {
        super();
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.patron = patron;
        this.librarian = librarian;
        this.loanDetails = new ArrayList<LoanDetail>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Patron getPatron() { return patron; }
    public void setPatron(Patron patron) { this.patron = patron; }
    public SystemUser getLibrarian() { return librarian; }
    public void setLibrarian(SystemUser librarian) { this.librarian = librarian; }
    public List<LoanDetail> getLoanDetails() { return loanDetails; }
    public void setLoanDetails(List<LoanDetail> loanDetails) { this.loanDetails = loanDetails; }

    public void addLoanDetail(LoanDetail detail) {
        this.loanDetails.add(detail);
        detail.setLoanRecord(this);
    }
}