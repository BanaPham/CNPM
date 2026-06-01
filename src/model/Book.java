package model;
 
import java.io.Serializable;
 
public class Book implements Serializable {
	private String bookID;
	private String title;
	private String isbn;
	private int publishYear;
	private String deDCcode;
	private double price;
	private String summary;
 
	public Book() {
    	super();
	}
 
	public Book(String bookID, String title, String isbn, int publishYear, String deDCcode, double price,
        	String summary) {
    	super();
    	this.bookID = bookID;
    	this.title = title;
    	this.isbn = isbn;
        this.publishYear = publishYear;
    	this.deDCcode = deDCcode;
    	this.price = price;
    	this.summary = summary;
	}
 
	public String getBookID() {
    	return bookID;
	}
 
	public void setBookID(String bookID) {
    	this.bookID = bookID;
	}
 
	public String getTitle() {
    	return title;
	}
 
	public void setTitle(String title) {
    	this.title = title;
	}
 
	public String getIsbn() {
    	return isbn;
	}
 
	public void setIsbn(String isbn) {
    	this.isbn = isbn;
	}
 
	public int getPublishYear() {
    	return publishYear;
	}
 
	public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
	}
 
	public String getDeDCcode() {
    	return deDCcode;
	}
 
	public void setDeDCcode(String deDCcode) {
    	this.deDCcode = deDCcode;
	}
 
	public double getPrice() {
    	return price;
	}
 
	public void setPrice(double price) {
    	this.price = price;
	}
 
	public String getSummary() {
    	return summary;
	}
 
	public void setSummary(String summary) {
    	this.summary = summary;
	}
}
