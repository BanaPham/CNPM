package model;
 
import java.io.Serializable;
import java.sql.Date;
 
public class Patron implements Serializable {
	private String patronID;
	private String name;
	private String phone;
	private String email;
	private String cardType;
	private Date expiryDate;
	private String username;
	private String password;
 
	public Patron() {
    	super();
	}
 
	public Patron(String patronID, String name, String phone, String email, String cardType, Date expiryDate,
        	String username, String password) {
    	super();
    	this.patronID = patronID;
    	this.name = name;
    	this.phone = phone;
    	this.email = email;
    	this.cardType = cardType;
        this.expiryDate = expiryDate;
    	this.username = username;
    	this.password = password;
	}
 
	public String getPatronID() {
    	return patronID;
	}
 
	public void setPatronID(String patronID) {
    	this.patronID = patronID;
	}
 
	public String getName() {
    	return name;
	}
 
	public void setName(String name) {
    	this.name = name;
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
}
