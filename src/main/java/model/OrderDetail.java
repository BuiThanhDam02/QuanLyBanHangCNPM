package model;

import java.io.Serializable;
import java.sql.Date;

public class OrderDetail implements Serializable {

    private int id  ;
    private int productId  ;
    private int orderId  ;
    private int numOfProducts ;
    private float totalPrice;
    private int status ;

    public OrderDetail(int id, int productId, int orderId, int numOfProducts, float totalPrice, int status) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
        this.numOfProducts = numOfProducts;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", numOfProducts=" + numOfProducts +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
