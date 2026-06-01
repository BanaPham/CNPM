package dao;
import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    protected Connection con;

    public DAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost;databaseName=CNPM;encrypt=true;trustServerCertificate=true;";
            String user = "sa";
            String password = "123456";

            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connect to SQL Server success!");
        } catch (Exception e) {
            System.err.println("Kết nối thất bại!");
            e.printStackTrace();
        }
    }
}