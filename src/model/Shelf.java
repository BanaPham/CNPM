package model;
 
import java.io.Serializable;
 
public class Shelf implements Serializable {
	private String shelfID;
	private String room;
	private String row;
 
	public Shelf() {
    	super();
	}
 
	public Shelf(String shelfID, String room, String row) {
    	super();
    	this.shelfID = shelfID;
    	this.room = room;
    	this.row = row;
	}
 
	public String getShelfID() {
    	return shelfID;
	}
 
	public void setShelfID(String shelfID) {
    	this.shelfID = shelfID;
	}
 
	public String getRoom() {
    	return room;
	}
 
	public void setRoom(String room) {
    	this.room = room;
	}
 
	public String getRow() {
    	return row;
	}
 
	public void setRow(String row) {
    	this.row = row;
	}
}
