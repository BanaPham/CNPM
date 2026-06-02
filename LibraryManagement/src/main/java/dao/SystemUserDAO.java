package dao;

import model.SystemUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SystemUserDAO extends DAO {
    public SystemUserDAO() {
        super();
    }

    private int parseId(String userID) {
        if (userID != null) {
            String numeric = userID.replaceAll("[^0-9]", "");
            if (!numeric.isEmpty()) {
                try {
                    return Integer.parseInt(numeric);
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        return 0;
    }

    private SystemUser mapSystemUser(ResultSet rs) throws Exception {
        SystemUser user = new SystemUser();
        user.setUserID(rs.getString("userID"));
        user.setUsername(rs.getString("username"));
        
        String dbRole = rs.getString("role");
        String mappedRole = dbRole;
        if ("Admin".equalsIgnoreCase(dbRole)) mappedRole = "Quản trị viên";
        else if ("Librarian".equalsIgnoreCase(dbRole)) mappedRole = "Thủ thư";
        else if ("Staff".equalsIgnoreCase(dbRole)) mappedRole = "Nhân viên";
        else if ("Manager".equalsIgnoreCase(dbRole)) mappedRole = "Quản lý";
        user.setRole(mappedRole);
        
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("fullName"));
        user.setEmail(rs.getString("email"));
        
        String dbStatus = rs.getString("status");
        String mappedStatus = dbStatus;
        if ("active".equalsIgnoreCase(dbStatus)) mappedStatus = "Active";
        else if ("locked".equalsIgnoreCase(dbStatus)) mappedStatus = "Locked";
        user.setStatus(mappedStatus);
        
        user.setCreatedDate(rs.getDate("createdDate"));
        user.setLockReason(rs.getString("lockReason"));
        user.setLockUntil(rs.getDate("lockUntil"));
        return user;
    }

    public SystemUser checkLogin(String username, String password) {
        SystemUser user = null;
        String sql = "SELECT CONCAT('US', id) AS userID, username, role, password, name AS fullName, email, status, created_date AS createdDate, lock_reason AS lockReason, lock_until AS lockUntil "
                + "FROM tblsystemuser WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = mapSystemUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<SystemUser> getSystemUserList() {
        ArrayList<SystemUser> result = new ArrayList<SystemUser>();
        String sql = "SELECT CONCAT('US', id) AS userID, username, role, password, name AS fullName, email, status, created_date AS createdDate, lock_reason AS lockReason, lock_until AS lockUntil "
                + "FROM tblsystemuser ORDER BY id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapSystemUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public SystemUser getSystemUserDetail(String userID) {
        SystemUser user = null;
        String sql = "SELECT CONCAT('US', id) AS userID, username, role, password, name AS fullName, email, status, created_date AS createdDate, lock_reason AS lockReason, lock_until AS lockUntil "
                + "FROM tblsystemuser WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, parseId(userID));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = mapSystemUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean lockUser(String userID, String reason, java.sql.Date lockUntil) {
        if (!canLockUser(userID)) {
            return false;
        }
        String sql = "UPDATE tblsystemuser SET status = ?, lock_reason = ?, lock_until = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Locked");
            ps.setString(2, reason);
            ps.setDate(3, lockUntil);
            ps.setInt(4, parseId(userID));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlockUser(String userID) {
        String sql = "UPDATE tblsystemuser SET status = ?, lock_reason = NULL, lock_until = NULL WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Active");
            ps.setInt(2, parseId(userID));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRole(String userID, String role) {
        String dbRole = role;
        if ("Quản trị viên".equalsIgnoreCase(role)) dbRole = "Admin";
        else if ("Thủ thư".equalsIgnoreCase(role)) dbRole = "Librarian";
        else if ("Nhân viên".equalsIgnoreCase(role)) dbRole = "Staff";
        else if ("Quản lý".equalsIgnoreCase(role)) dbRole = "Manager";

        String sql = "UPDATE tblsystemuser SET role = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dbRole);
            ps.setInt(2, parseId(userID));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getRoleList() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("Quản trị viên");
        result.add("Thủ thư");
        result.add("Nhân viên");
        result.add("Quản lý");
        return result;
    }

    public int countActiveAdmin() {
        int result = 0;
        String sql = "SELECT COUNT(*) AS total FROM tblsystemuser WHERE role = ? AND status = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Admin");
            ps.setString(2, "Active");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean canLockUser(String userID) {
        SystemUser user = getSystemUserDetail(userID);
        if (user == null) {
            return false;
        }
        if ("Quản trị viên".equalsIgnoreCase(user.getRole()) && "Active".equalsIgnoreCase(user.getStatus())) {
            return countActiveAdmin() > 1;
        }
        return true;
    }
}
