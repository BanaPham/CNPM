package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/library_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Ho_Chi_Minh";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Admin@123"; 
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
