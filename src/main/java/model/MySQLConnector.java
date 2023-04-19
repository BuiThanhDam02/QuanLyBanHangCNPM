package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    static final String dataBaseURL = "jdbc:mysql://localhost:3306/CNPM_BanHang_Nhom11";
    static final String username = "root";
    static final String password = "212901261"; // thay đổi mật khẩu theo mật khẩu mysql của máy

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dataBaseURL, username, password);
    }
}
