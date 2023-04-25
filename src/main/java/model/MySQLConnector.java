package model;

import model.dao.ProductDAO;

import java.sql.*;

public class MySQLConnector {
    static final String dataBaseURL = "jdbc:mysql://localhost:3306/CNPM_BanHang_Nhom11";
    static final String username = "root";
    static final String password = "212901261"; // thay đổi mật khẩu theo mật khẩu mysql của máy

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dataBaseURL, username, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;

    }

    public static void main(String[] args)  {
//        try {
//            String[][] data = new ProductDAO().readProductsTableData();
//            for(int i = 0; i < data.length; i++) {
//                for(int j = 0; j < data[i].length; j++) {
//                    System.out.print(data[i][j]);
//                }
//                System.out.println("");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }
}
