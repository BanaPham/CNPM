package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "admin"; 
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    protected static Connection con = null;

    public DAO() {
        getConnection();
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName(DRIVER_CLASS);
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Kết nối đến cơ sở dữ liệu library_db THÀNH CÔNG!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy MySQL Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi: Kết nối thất bại!");
            e.printStackTrace();
        }
        return con;
    }
}
