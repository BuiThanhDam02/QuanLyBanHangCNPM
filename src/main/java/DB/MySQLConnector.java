package DB;

import model.dao.ProductDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class MySQLConnector {


    public static Connection getConnection(boolean isCreated) throws SQLException {
        String dbName = "";
        if (isCreated==true){
            dbName=DBProperties.getDbName();
        }
        Connection conn = null;
         final String dataBaseURL = "jdbc:mysql://"+DBProperties.getDbHost()+":"+DBProperties.getDbPort()+"/"+dbName;
         final String username = DBProperties.getDbUsername();
         final String password = DBProperties.getDbPassword();
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
    public static void excuteSql(){
        try {
            File f = new File("Swing_store_MySQL_script.sql");

            Connection con = getConnection(false);
            ScriptRunner runner = new ScriptRunner(con,false, false);
            String file = f.getAbsolutePath();;
            runner.runScript(new BufferedReader(new FileReader(file)));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args)  {

    }
}
