package model;

import java.io.Serializable;

public class SystemUser implements Serializable {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private String email;
    private String status = "Active";
    private java.sql.Date createdDate;
    private String lockReason;
    private java.sql.Date lockUntil;

    public SystemUser() {
        super();
    }

    public SystemUser(String fullName) {
        super();
        this.fullName = fullName;
    }

    public SystemUser(String systemUserID, String username, String password, String role) {
        super();
        if (systemUserID != null && !systemUserID.trim().isEmpty()) {
            try {
                String numeric = systemUserID.replaceAll("[^0-9]", "");
                if (!numeric.isEmpty()) {
                    this.id = Integer.parseInt(numeric);
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        this.username = username;
        this.password = password;
        this.fullName = username; // default to username
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Mapping for systemUserID
    public String getSystemUserID() { return "US" + id; }
    public void setSystemUserID(String systemUserID) {
        if (systemUserID != null && !systemUserID.trim().isEmpty()) {
            try {
                String numeric = systemUserID.replaceAll("[^0-9]", "");
                if (!numeric.isEmpty()) {
                    this.id = Integer.parseInt(numeric);
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
    }

    public String getUserID() { return getSystemUserID(); }
    public void setUserID(String userID) { setSystemUserID(userID); }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public java.sql.Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(java.sql.Date createdDate) { this.createdDate = createdDate; }

    public String getLockReason() { return lockReason; }
    public void setLockReason(String lockReason) { this.lockReason = lockReason; }

    public java.sql.Date getLockUntil() { return lockUntil; }
    public void setLockUntil(java.sql.Date lockUntil) { this.lockUntil = lockUntil; }
}
