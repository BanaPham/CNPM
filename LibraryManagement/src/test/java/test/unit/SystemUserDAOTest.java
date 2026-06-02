package test.unit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dao.DAO;
import dao.SystemUserDAO;
import model.SystemUser;

public class SystemUserDAOTest {
    private SystemUserDAO sud = new SystemUserDAO();

    @Test
    public void testCheckLoginAdminActive() {
        SystemUser user = sud.checkLogin("user001", "pass001");
        Assert.assertNotNull(user);
        Assert.assertEquals("US1", user.getUserID());
        Assert.assertEquals("Quản trị viên", user.getRole());
        Assert.assertEquals("Active", user.getStatus());
    }

    @Test
    public void testCheckLoginWrongPassword() {
        SystemUser user = sud.checkLogin("user001", "wrong-pass");
        Assert.assertNull(user);
    }

    @Test
    public void testCheckLoginLockedUser() {
        SystemUser user = sud.checkLogin("user010", "pass010");
        Assert.assertNotNull(user);
        Assert.assertEquals("Locked", user.getStatus());
    }

    @Test
    public void testGetSystemUserList() {
        ArrayList<SystemUser> list = sud.getSystemUserList();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
        Assert.assertNotNull(list.get(0).getUserID());
        Assert.assertNotNull(list.get(0).getUsername());
    }

    @Test
    public void testGetSystemUserDetailStandard() {
        SystemUser user = sud.getSystemUserDetail("US1");
        Assert.assertNotNull(user);
        Assert.assertEquals("user001", user.getUsername());
        Assert.assertEquals("Quản trị viên", user.getRole());
    }

    @Test
    public void testGetSystemUserDetailNotFound() {
        SystemUser user = sud.getSystemUserDetail("US999");
        Assert.assertNull(user);
    }

    @Test
    public void testLockUserStandard() {
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);
            Date until = Date.valueOf("2026-12-31");
            boolean result = sud.lockUser("US2", "JUnit test lock", until);
            Assert.assertTrue(result);

            SystemUser user = sud.getSystemUserDetail("US2");
            Assert.assertEquals("Locked", user.getStatus());
            Assert.assertEquals("JUnit test lock", user.getLockReason());
            Assert.assertEquals(until, user.getLockUntil());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void testUnlockUserStandard() {
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);
            sud.lockUser("US2", "JUnit test lock", Date.valueOf("2026-12-31"));
            boolean result = sud.unlockUser("US2");
            Assert.assertTrue(result);

            SystemUser user = sud.getSystemUserDetail("US2");
            Assert.assertEquals("Active", user.getStatus());
            Assert.assertNull(user.getLockReason());
            Assert.assertNull(user.getLockUntil());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void testUpdateRoleStandard() {
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);
            boolean result = sud.updateRole("US3", "Thủ thư");
            Assert.assertTrue(result);

            SystemUser user = sud.getSystemUserDetail("US3");
            Assert.assertEquals("Thủ thư", user.getRole());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void testCanNotLockOnlyActiveAdmin() {
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(
                "UPDATE tblsystemuser SET status='Locked' " +
                "WHERE role='Admin' AND id <> 1");
            ps.executeUpdate();

            Assert.assertEquals(1, sud.countActiveAdmin());
            Assert.assertFalse(sud.canLockUser("US1"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
