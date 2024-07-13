package com.company.service;
import com.company.dao.RatingDAO;
import com.company.model.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RatingAnalysis {
    private static Connection connection;

    public RatingAnalysis(Connection connection) {
        RatingAnalysis.connection = connection;
    }

    public static void RatingAnalysisMenu(Scanner scanner){
        boolean back = false;
        while (!back) {
            System.out.println("\n***** Rating Analysis *****");
            System.out.println("1. Calculate average rating for a product");
            System.out.println("2. Analyze ratings distribution");
            System.out.println("3. View top-rated products");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        calculateAverageRating(scanner);
                        break;
                    case 2:
                        analyzeRatingsDistribution(scanner);
                        break;
                    case 3:
                        viewTopRatedProducts( scanner);
                        break;
                    case 4:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void calculateAverageRating(Scanner scanner) throws SQLException {
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        double averageRating = RatingDAO.calculateAverageRating(productId,connection);
        System.out.printf("Average rating for product %d: %.2f\n", productId, averageRating);
    }

    private static void viewTopRatedProducts(Scanner scanner) throws SQLException {
        System.out.print("Enter the number of top-rated products to display: ");
        int limit = scanner.nextInt();
        List<Product> topRatedProducts = RatingDAO.getTopRatedProducts(limit,connection);
        System.out.println("\nTop-rated products:");
        for (Product product : topRatedProducts) {
            System.out.printf("%d. %s (ID: %d) - Price: $%.2f\n",
                    topRatedProducts.indexOf(product) + 1,
                    product.getProductName(),
                    product.getProductId(),
                    product.getPrice());
        }
    }

    private static void analyzeRatingsDistribution(Scanner scanner) {
        try {
            System.out.print("Enter product ID: ");
            int productId = scanner.nextInt();
            scanner.nextLine();

            Map<Integer, Integer> distribution = RatingDAO.getRatingsDistribution(productId,connection);

            System.out.println("\nRatings distribution for product " + productId + ":");
            int totalRatings = 0;
            for (int i = 1; i <= 5; i++) {
                int count = distribution.get(i);
                totalRatings += count;
                System.out.printf("%d-star: %d\n", i, count);
            }
        } catch (SQLException e) {
            System.out.println("Error analyzing ratings distribution: " + e.getMessage());
        }
    }




}
