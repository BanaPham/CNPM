package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    protected static Connection con;

    public DAO() {
        if (con == null) {
            String dbUrl = "jdbc:mysql://localhost:3306/db_library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String dbClass = "com.mysql.cj.jdbc.Driver";
            try {
                Class.forName(dbClass);
                con = DriverManager.getConnection(dbUrl, "root", "123456789");
                System.out.println("Kết nối Cơ sở dữ liệu thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Kết nối Cơ sở dữ liệu thất bại!");
            }
        }
    }
}
