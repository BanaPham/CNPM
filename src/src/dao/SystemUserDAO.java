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

    private SystemUser mapSystemUser(ResultSet rs) throws Exception {
        SystemUser user = new SystemUser();
        user.setUserID(rs.getString("userID"));
        user.setUsername(rs.getString("username"));
        user.setRole(rs.getString("role"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("fullName"));
        user.setEmail(rs.getString("email"));
        user.setStatus(rs.getString("status"));
        user.setCreatedDate(rs.getDate("createdDate"));
        user.setLockReason(rs.getString("lockReason"));
        user.setLockUntil(rs.getDate("lockUntil"));
        return user;
    }

    public SystemUser checkLogin(String username, String password) {
        SystemUser user = null;
        String sql = "SELECT userID, username, role, password, fullName, email, status, createdDate, lockReason, lockUntil "
                + "FROM tblSystemUser WHERE username = ? AND password = ?";
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
        String sql = "SELECT userID, username, role, password, fullName, email, status, createdDate, lockReason, lockUntil "
                + "FROM tblSystemUser ORDER BY userID";
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
        String sql = "SELECT userID, username, role, password, fullName, email, status, createdDate, lockReason, lockUntil "
                + "FROM tblSystemUser WHERE userID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userID);
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
        String sql = "UPDATE tblSystemUser SET status = ?, lockReason = ?, lockUntil = ? WHERE userID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Locked");
            ps.setString(2, reason);
            ps.setDate(3, lockUntil);
            ps.setString(4, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlockUser(String userID) {
        String sql = "UPDATE tblSystemUser SET status = ?, lockReason = NULL, lockUntil = NULL WHERE userID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Active");
            ps.setString(2, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRole(String userID, String role) {
        String sql = "UPDATE tblSystemUser SET role = ? WHERE userID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, role);
            ps.setString(2, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getRoleList() {
        ArrayList<String> result = new ArrayList<String>();
        String sql = "SELECT DISTINCT role FROM tblSystemUser ORDER BY role";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int countActiveAdmin() {
        int result = 0;
        String sql = "SELECT COUNT(*) AS total FROM tblSystemUser WHERE role = ? AND status = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Quản trị viên");
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
