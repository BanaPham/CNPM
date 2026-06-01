package model;

import java.io.Serializable;
import java.util.Date;

public class LoanDetail implements Serializable {
    private int id;
    private Item item;
    private LoanRecord loanRecord;
    private Date actualReturnDate;
    private double fineAmount;
    private String statusNotes;

    public LoanDetail() {
        super();
    }

    public LoanDetail(Date actualReturnDate, double fineAmount, String statusNotes, Item item) {
        super();
        this.actualReturnDate = actualReturnDate;
        this.fineAmount = fineAmount;
        this.statusNotes = statusNotes;
        this.item = item;
    }

    // Constructor used by TraSach
    public LoanDetail(Item item, Date returnDate) {
        super();
        this.item = item;
        this.actualReturnDate = returnDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    public LoanRecord getLoanRecord() { return loanRecord; }
    public void setLoanRecord(LoanRecord loanRecord) { this.loanRecord = loanRecord; }
    
    public Date getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(Date actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    
    // Alias for TraSach
    public Date getReturnDate() { return actualReturnDate; }
    public void setReturnDate(Date returnDate) { this.actualReturnDate = returnDate; }

    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }
    public String getStatusNotes() { return statusNotes; }
    public void setStatusNotes(String statusNotes) { this.statusNotes = statusNotes; }
}
