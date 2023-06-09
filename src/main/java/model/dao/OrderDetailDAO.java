package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.MySQLConnector;

import model.OrdersDetail;

public class OrderDetailDAO {
	private final Connection connection;
	private String query;
	private PreparedStatement statement;
	private int insertedLines = 0;

	public OrderDetailDAO() throws SQLException {
		connection = MySQLConnector.getConnection(true);
	}
    /*
        Nguoi thuc hien Bao
        Tao orderDetail
    */

	public boolean createOrderDetail(OrdersDetail orderDetail) throws SQLException {
		query = "INSERT INTO Orders_Detail(productId,orderId,numOfProducts,totalPrice,status) VALUES( ?, ?,?,?,?)";
		statement = connection.prepareStatement(query);
		statement.setInt(1, orderDetail.getProductId());
		statement.setInt(2, orderDetail.getOrderId());
		statement.setInt(3, orderDetail.getNumOfProducts());
		statement.setFloat(4, orderDetail.getTotalPrice());
		statement.setInt(5, orderDetail.getStatus());
		insertedLines = statement.executeUpdate();
		return (insertedLines != 0);

	}

	/*
	 * Nguoi thuc hien: Luan
	 * them deleteOrderDetail
	 */
	public boolean deleteOrderDetail(int orderDetailId) throws SQLException {
		query = "DELETE FROM Orders_Detail WHERE id=?";
		statement = connection.prepareStatement(query);
		statement.setInt(1, orderDetailId);
		int deletedLines = statement.executeUpdate();
		return (deletedLines != 0);
	}
       public void close() throws SQLException {
        connection.close();
    }
}
