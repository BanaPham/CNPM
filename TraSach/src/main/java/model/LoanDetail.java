package model;

import java.io.Serializable;
import java.util.Date;

public class LoanDetail implements Serializable {
    private int id;
    private Item item;
    private Date returnDate;
    private LoanRecord loanRecord;

    public LoanDetail() {
        super();
    }

    public LoanDetail(Item item, Date returnDate) {
        super();
        this.item = item;
        this.returnDate = returnDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public LoanRecord getLoanRecord() { return loanRecord; }
    public void setLoanRecord(LoanRecord loanRecord) { this.loanRecord = loanRecord; }
}
