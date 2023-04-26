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
import model.Product;

public class ProductDAO {
    private final Connection connection;
    private String query;
    private PreparedStatement statement;
    private int insertedLines = 0;

    public ProductDAO() throws SQLException {
        connection = MySQLConnector.getConnection();
    }


    public boolean createProduct(Product product) throws SQLException {
        // Use Case Thêm món ăn (20130217-Bùi Thanh Đảm)
        // Bước 1.5 : Hệ thông xác thực món ăn và lưu vào cơ sở dữ liệu
        query = "INSERT INTO Products VALUES(?, ?, ?, ?,?,?,?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, product.getId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getImageUrl());
        statement.setFloat(4, product.getPrice());
        statement.setString(5, product.getCategory());
        statement.setInt(6, product.getStatus());
        statement.setString(7, product.getDescription());


        insertedLines = statement.executeUpdate();
//        close();
        return(insertedLines != 0);
    }

    public String[][] readProductsTableData() throws SQLException {
        int rows = 0, columns = 7, aux = 0;
        ResultSet resultRows = connection.prepareStatement("SELECT COUNT(*) FROM Products").executeQuery();
        if(resultRows.next()) rows = resultRows.getInt(1);

        query = "SELECT * FROM Products";
        statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        List<String> productsList = new ArrayList<>();
        while(result.next()){

            productsList.add(result.getInt("id")+"");
            productsList.add(result.getString("name"));
            productsList.add(result.getString("imageUrl"));
            float price = result.getFloat("price");
            Locale locale = new Locale("vi");
            NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
            String priceFormat = format.format(price).split(",")[0];

            productsList.add(priceFormat);
            productsList.add(result.getString("category"));
            productsList.add(result.getInt("status")+"");
            productsList.add(result.getString("description"));
        }

        String[][] products = new String[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                products[i][j] = productsList.get(aux++);
            }
        }
        return products;
    }

    public boolean deleteProduct(int id) throws SQLException {
        // Use Case Xóa món ăn (20130217-Bùi Thanh Đảm)
        // Bước 2.8 : Hệ thống ghi thông tin món ăn bị xóa vào cơ sở dữ liệu
        query = "DELETE FROM Products WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
