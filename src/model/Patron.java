package model;

import java.util.Date;

public class Patron {
    private String patronID;
    private String fullName;
    private Date dob;
    private String cccd;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String cardType;
    private Date expiryDate;
    private String status;

    public Patron() {
    }

    public Patron(String patronID, String fullName, Date dob, String cccd, String phone, 
                  String email, String address, String avatar, String cardType, Date expiryDate, String status) {
        this.patronID = patronID;
        this.fullName = fullName;
        this.dob = dob;
        this.cccd = cccd;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        this.cardType = cardType;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public String getPatronID() { return patronID; }
    public void setPatronID(String patronID) { this.patronID = patronID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

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

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}