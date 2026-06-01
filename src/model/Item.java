package model;
 
import java.io.Serializable;
 
public class Item implements Serializable {
	private String itemID;
	private String barCode;
	private String status;
	private String tblBookID;
	private String tblShelfID;
 
	public Item() {
    	super();
	}
 
	public Item(String itemID, String barCode, String status, String tblBookID, String tblShelfID) {
    	super();
    	this.itemID = itemID;
    	this.barCode = barCode;
    	this.status = status;
    	this.tblBookID = tblBookID;
        this.tblShelfID = tblShelfID;
	}
 
	public String getItemID() {
    	return itemID;
	}
 
	public void setItemID(String itemID) {
    	this.itemID = itemID;
	}
 
	public String getBarCode() {
    	return barCode;
	}
 
	public void setBarCode(String barCode) {
    	this.barCode = barCode;
	}
 
	public String getStatus() {
    	return status;
	}
 
	public void setStatus(String status) {
    	this.status = status;
	}
 
	public String getTblBookID() {
    	return tblBookID;
	}
 
	public void setTblBookID(String tblBookID) {
    	this.tblBookID = tblBookID;
	}
 
	public String getTblShelfID() {
    	return tblShelfID;
	}
 
	public void setTblShelfID(String tblShelfID) {
        this.tblShelfID = tblShelfID;
	}
}
