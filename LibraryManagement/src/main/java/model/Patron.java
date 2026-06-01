package model;

import java.io.Serializable;
import java.util.Date;

public class Patron implements Serializable {
    private int id;
    private String cardNumber;
    private String fullName;
    private double outstandingDebt;
    private Date dob;
    private String cccd;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String status;
    private String cardType;
    private Date expiryDate;
    private String username;
    private String password;

    public Patron() {
        super();
    }

    public Patron(String cardNumber, String fullName, double outstandingDebt) {
        super();
        this.cardNumber = cardNumber;
        this.fullName = fullName;
        this.outstandingDebt = outstandingDebt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public double getOutstandingDebt() { return outstandingDebt; }
    public void setOutstandingDebt(double outstandingDebt) { this.outstandingDebt = outstandingDebt; }

    // Mappings for patronID from root project
    public String getPatronID() { return cardNumber; }
    public void setPatronID(String patronID) { this.cardNumber = patronID; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
