package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
// nguoi thuc hien Bui Thanh Dam 20130217
	public String[][] readOrdersTableData() throws SQLException {
		int rows = 0, columns = 6, aux = 0;
		ResultSet resultRows = connection.prepareStatement("SELECT COUNT(*) FROM Orders").executeQuery();
		if(resultRows.next()) rows = resultRows.getInt(1);

		query = "SELECT * FROM Orders";
		statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		List<String> orderList = new ArrayList<>();
		while(result.next()){

			orderList.add(result.getInt("id")+"");
			orderList.add(result.getDate("createAt").toString());
			orderList.add(result.getInt("numOfProducts")+"");
			float price = result.getFloat("totalPrice");
			Locale locale = new Locale("vi");
			NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
			String priceFormat = format.format(price).split(",")[0];

			orderList.add(priceFormat);

			orderList.add(result.getInt("status")+"");
			orderList.add(result.getString("description"));
		}

		String[][] orders = new String[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				orders[i][j] = orderList.get(aux++);
			}
		}
		return orders;
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
