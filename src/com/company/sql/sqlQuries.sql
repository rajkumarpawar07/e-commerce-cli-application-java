CREATE DATABASE ecommerce;
use ecommerce;

CREATE TABLE Category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

INSERT INTO Category VALUES (1,"cloths");

CREATE TABLE Product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

select * from Product;

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL
);

insert into Customer values(1,"abc");
select * from Customer;

CREATE TABLE Review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    customer_id INT,
    review_text TEXT,
    rating INT,
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

select * from Review;

CREATE TABLE Rating (
    rating_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    rating_value INT,
    rating_date DATE,
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);