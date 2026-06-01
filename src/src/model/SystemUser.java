package model;

import java.io.Serializable;
import java.sql.Date;

public class SystemUser implements Serializable {
    private String userID;
    private String username;
    private String role;
    private String password;
    private String fullName;
    private String email;
    private String status;
    private Date createdDate;
    private String lockReason;
    private Date lockUntil;

    public SystemUser() {
    }

    public SystemUser(String userID, String username, String role, String password, String fullName, String email,
            String status, Date createdDate, String lockReason, Date lockUntil) {
        this.userID = userID;
        this.username = username;
        this.role = role;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.createdDate = createdDate;
        this.lockReason = lockReason;
        this.lockUntil = lockUntil;
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public String getLockReason() { return lockReason; }
    public void setLockReason(String lockReason) { this.lockReason = lockReason; }

    public Date getLockUntil() { return lockUntil; }
    public void setLockUntil(Date lockUntil) { this.lockUntil = lockUntil; }
}
