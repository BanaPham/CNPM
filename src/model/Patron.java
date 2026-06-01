package model;
import java.util.Date;

public class Patron {
    private String patronID;
    private String username;
    private String password;
    private String fullName;
    private Date dob;
    private String cccd;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String status;
    private String cardType;
    private Date expiryDate;

    public Patron() {
    }

    public Patron(String patronID, String username, String password,
                  String fullName, Date dob, String cccd,
                  String phone, String email, String address,
                  String avatar, String status,
                  String cardType, Date expiryDate) {
        this.patronID = patronID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dob = dob;
        this.cccd = cccd;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        this.status = status;
        this.cardType = cardType;
        this.expiryDate = expiryDate;
    }

    public String getPatronID() {
        return patronID;
    }

    public void setPatronID(String patronID) {
        this.patronID = patronID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
