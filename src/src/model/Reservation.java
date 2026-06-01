package model;

import java.io.Serializable;
import java.sql.Date;

public class Reservation implements Serializable {
    private String reservID;
    private Date resDate;
    private String status;
    private String tblPatronID;
    private String tblBookID;

    public Reservation() {
    }

    public Reservation(String reservID, Date resDate, String status, String tblPatronID, String tblBookID) {
        this.reservID = reservID;
        this.resDate = resDate;
        this.status = status;
        this.tblPatronID = tblPatronID;
        this.tblBookID = tblBookID;
    }

    public String getReservID() { return reservID; }
    public void setReservID(String reservID) { this.reservID = reservID; }

    public Date getResDate() { return resDate; }
    public void setResDate(Date resDate) { this.resDate = resDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTblPatronID() { return tblPatronID; }
    public void setTblPatronID(String tblPatronID) { this.tblPatronID = tblPatronID; }

    public String getTblBookID() { return tblBookID; }
    public void setTblBookID(String tblBookID) { this.tblBookID = tblBookID; }
}
