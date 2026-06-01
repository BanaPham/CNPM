package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class LoanRecord implements Serializable {
    private int id;
    private Date borrowDate;
    private Date dueDate;
    private Patron patron;
    private SystemUser librarian;
    private double fineAmount;
    private List<LoanDetail> loanDetails;

    public LoanRecord() {
        super();
        this.loanDetails = new ArrayList<>();
    }

    public LoanRecord(Date borrowDate, Date dueDate, Patron patron, SystemUser librarian) {
        super();
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.patron = patron;
        this.librarian = librarian;
        this.loanDetails = new ArrayList<>();
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
    
    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    public List<LoanDetail> getLoanDetails() { return loanDetails; }
    public void setLoanDetails(List<LoanDetail> loanDetails) { this.loanDetails = loanDetails; }

    // Alias for TraSach
    public List<LoanDetail> getDetails() { return loanDetails; }
    public void setDetails(List<LoanDetail> details) { this.loanDetails = details; }

    public void addLoanDetail(LoanDetail detail) {
        this.loanDetails.add(detail);
        detail.setLoanRecord(this);
    }

    public double calculateFine(double coverPrice) {
        Date today = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(today);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.dueDate);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        if (cal1.after(cal2)) {
            long diff = cal1.getTimeInMillis() - cal2.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            if (days > 0) {
                return 0.20 * coverPrice;
            }
        }
        return 0.0;
    }
}
