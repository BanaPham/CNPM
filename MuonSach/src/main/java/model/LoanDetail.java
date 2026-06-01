package model;

import java.io.Serializable;
import java.util.Date;

public class LoanDetail implements Serializable {
    private int id;
    private Date actualReturnDate;
    private float fineAmount;
    private String statusNotes;
    private Item item;
    private LoanRecord loanRecord;

    public LoanDetail() {
        super();
    }

    public LoanDetail(Date actualReturnDate, float fineAmount, String statusNotes, Item item) {
        super();
        this.actualReturnDate = actualReturnDate;
        this.fineAmount = fineAmount;
        this.statusNotes = statusNotes;
        this.item = item;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(Date actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    public float getFineAmount() { return fineAmount; }
    public void setFineAmount(float fineAmount) { this.fineAmount = fineAmount; }
    public String getStatusNotes() { return statusNotes; }
    public void setStatusNotes(String statusNotes) { this.statusNotes = statusNotes; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    public LoanRecord getLoanRecord() { return loanRecord; }
    public void setLoanRecord(LoanRecord loanRecord) { this.loanRecord = loanRecord; }
}