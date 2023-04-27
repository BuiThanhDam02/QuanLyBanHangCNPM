package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.MySQLConnector;
import model.Order;

public class OrderDAO {
	  	private final Connection connection;
	    private String query;
	    private PreparedStatement statement;
	    private int insertedLines = 0;
	    
	    public OrderDAO() throws SQLException{
	    	connection = MySQLConnector.getConnection();
	    }
	    /*
	     * Nguoi thuc hien : Bao
	     * Them order vao database
	     */
	    public boolean createOrder(Order orders) throws SQLException {
	    	 query = "INSERT INTO Orders VALUES(?, ?, ?, ?,?,?)";
	    	 statement = connection.prepareStatement(query);
	         statement.setInt(1, orders.getId());
	         statement.setDate(2, orders.getCreateAt());
	         statement.setInt(3, orders.getNumOfProducts());
	         statement.setFloat(4, orders.getTotalPrice());
	         statement.setInt(5, orders.getStatus());
	         statement.setString(6, orders.getDescription());
	         insertedLines = statement.executeUpdate();
	        return(insertedLines != 0);
	    	
	    }
		/*
		 * Nguoi thuc hien: Luan
		 * Them chuc nang xoa order khoi database
		 */
		public boolean deleteOrder(int orderId) throws SQLException {
			query = "DELETE FROM Orders WHERE id=?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, orderId);
			int deletedLines = statement.executeUpdate();
			return (deletedLines != 0);
		}

           public void close() throws SQLException {
        connection.close();
    }
}
