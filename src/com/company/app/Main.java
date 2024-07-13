package com.company.app;
import com.company.database.DatabaseConnection;
import com.company.service.ProductManagement;
import com.company.service.RatingAnalysis;
import com.company.service.ReviewSubmission;

import java.util.Scanner;
import java.sql.*;

public class Main {
    // class variables
    private static Connection connection;
    private static final Scanner scanner = new Scanner(System.in);

    // instances of services
    private static ProductManagement productManager;
    private static ReviewSubmission reviewManager;
    private static RatingAnalysis ratingAnalyzer;

    public static void main(String[] args) {
        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // assigning constructor values to the services
            productManager = new ProductManagement(connection);
            reviewManager = new ReviewSubmission(connection);
            ratingAnalyzer = new RatingAnalysis(connection);

            // get choices through command line
            int choice;
            do {
                System.out.println("----- Product Review and Rating System ------");
                System.out.println("1. Product Management");
                System.out.println("2. Review Submission");
                System.out.println("3. Rating Analysis");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        ProductManagement.ProductManagementMenu();
                        break;
                    case 2:
                        ReviewSubmission.ReviewSubmissionMenu(scanner);
                        break;
                    case 3:
                        RatingAnalysis.RatingAnalysisMenu(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        System.out.println();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        }catch(SQLException e){
            // database connection error
            System.out.println("Error in database connection: " + e.getMessage());
        }finally {
            try {
                // close the connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // closing error
                System.out.println("Error closing database connection: " + e.getMessage());
            }
            scanner.close();
        }
    }
}

