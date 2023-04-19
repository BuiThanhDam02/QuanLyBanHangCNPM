CREATE DATABASE CNPM_BanHang_Nhom11;
USE CNPM_BanHang_Nhom11;


CREATE TABLE Product(
	id INTEGER AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    imageUrl VARCHAR(255) ,
    price FLOAT NOT NULL,
    category VARCHAR(255),
    status INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Order(
    id INTEGER AUTO_INCREMENT,
    createAt DATETIME NOT NULL,
    numOfProducts INTEGER NOT NULL,
    totalPrice FLOAT NOT NULL,
    status INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);
CREATE TABLE Order_Detail(
                      id INTEGER AUTO_INCREMENT,
                      productId INTEGER NOT NULL REFERENCES Product(id),
                      orderId INTEGER NOT NULL REFERENCES Order(id),
                      numOfProducts INTEGER NOT NULL,
                      totalPrice FLOAT NOT NULL,
                      status INTEGER NOT NULL,
                      PRIMARY KEY(id)
);

-- DROP TABLE User;
-- DROP TABLE Product;
-- DROP TABLE Sale;



-- INSERT INTO Product VALUES(null, "Cheeseburger", "Burger", "9,99");
-- INSERT INTO Product VALUES(null, "Coca-Cola", "Drink", "3,99");


-- SELECT * FROM User;
-- SELECT * FROM Product;
-- SELECT * FROM Sale;
