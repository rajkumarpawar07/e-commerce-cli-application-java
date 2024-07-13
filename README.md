# Product Review and Rating System

## Prerequisites

Before you begin, ensure you have met the following requirements:
* You have installed Java Development Kit (JDK) 8 or higher
* You have installed MySQL Server


## Setup

To set up this project, follow these steps:

1. Clone the repository:
git clone https://github.com/rajkumarpawar07/e-commerce-cli-application-java.git

2. Setup your database:
Create a Use the newly created database and Create the necessary tables:
NOTE:- All SQL commands to create this is given in com.company.sql -> sqlQuries.sql

3. Congigure your JDBC 
Download JDBC Driver from https://dev.mysql.com/downloads/connector/j/
Import it your project structure

4. Steup Database connection
Open the DatabaseConnection.java file and update the database connection details
private static final String DB_URL = "jdbc:mysql://localhost:3306/product_review_db";
private static final String USER = "your_username";
private static final String PASS = "your_password";

5.Running the Application

