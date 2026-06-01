package model;

import java.io.Serializable;

public class Patron implements Serializable {
    private int id;
    private String cardNumber;
    private String fullName;
    private double outstandingDebt;

    public Patron() {
        super();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public double getOutstandingDebt() { return outstandingDebt; }
    public void setOutstandingDebt(double outstandingDebt) { this.outstandingDebt = outstandingDebt; }
}
