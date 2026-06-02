package test.unit;

import java.sql.Connection;
import java.sql.Statement;

public class FixDatabase {
    public static void main(String[] args) {
        new dao.DAO();
        Connection con = dao.DAO.con;
        if (con == null) {
            System.err.println("Could not connect to database!");
            System.exit(1);
        }
        try {
            Statement stmt = con.createStatement();
            // 1. Set status of all items that are NOT currently borrowed to 'Ready'
            String q1 = "UPDATE tblitem SET status = 'Ready' WHERE status = 'Borrowed' AND id NOT IN (SELECT item_id FROM tblloandetail WHERE actual_return_date IS NULL)";
            int rows1 = stmt.executeUpdate(q1);
            System.out.println("Updated " + rows1 + " returned items to 'Ready'.");

            // 2. Set status of all items that are currently borrowed to 'Borrowed'
            String q2 = "UPDATE tblitem SET status = 'Borrowed' WHERE id IN (SELECT item_id FROM tblloandetail WHERE actual_return_date IS NULL)";
            int rows2 = stmt.executeUpdate(q2);
            System.out.println("Updated " + rows2 + " active loan items to 'Borrowed'.");

            System.out.println("Database consistency fix completed successfully!");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
