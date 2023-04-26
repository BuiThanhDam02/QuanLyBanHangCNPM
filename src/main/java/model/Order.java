package model;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {

    private int id  ;
    private Date createAt ;
    private int numOfProducts ;
    private float totalPrice;
    private int status ;
    private String description ;

    public Order(int id, Date createAt, int numOfProducts, float totalPrice, int status, String description) {
        this.id = id;
        this.createAt = createAt;
        this.numOfProducts = numOfProducts;
        this.totalPrice = totalPrice;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createAt=" + createAt +
                ", numOfProducts=" + numOfProducts +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
