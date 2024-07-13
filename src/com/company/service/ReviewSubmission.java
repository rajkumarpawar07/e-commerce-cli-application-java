package com.company.service;
import com.company.dao.ReviewDAO;
import com.company.model.Product;
import com.company.model.Review;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ReviewSubmission {
    private static  Connection connection;

    public ReviewSubmission(Connection connection) {
        ReviewSubmission.connection = connection;
    }

    public static void ReviewSubmissionMenu(Scanner scanner) {
    boolean back = false;
    while (!back) {
        System.out.println("\n***** Review Management *****");
        System.out.println("1. Submit new review");
        System.out.println("2. View review details");
        System.out.println("3. Update review");
        System.out.println("4. Delete review");
        System.out.println("5. Back to main menu");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (choice) {
                case 1:
                    submitNewReview(scanner);
                    break;
                case 2:
                    viewReviewDetails(scanner);
                    break;
                case 3:
                    updateReview(scanner);
                    break;
                case 4:
                    deleteReview(scanner);
                    break;
                case 5:
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

    private static void submitNewReview(Scanner scanner) throws SQLException {
        // get the review details
        System.out.println("\nEnter review details:");
        System.out.print("Product ID: ");
        int productId = scanner.nextInt();
        Product product = ProductManagement.getProduct(productId);
        if(product==null){
            System.out.println("Product not found. Enter valid product ID");
            return;}
        int customerId = 1;
        System.out.println("Customer ID: "+customerId);
        scanner.nextLine();
        System.out.print("Review text: ");
        String reviewText = scanner.nextLine();
        System.out.print("Rating (1-5): ");
        int rating = scanner.nextInt();
        Review review = new Review(0, productId, customerId, reviewText, rating);

        // add new review in database
        ReviewDAO.addReview(review,connection);
        System.out.println("Review submitted successfully!!!");
    }

    private static void viewReviewDetails(Scanner scanner) throws SQLException {
        System.out.print("Enter review ID: ");
        int reviewId = scanner.nextInt();
        Review review = ReviewDAO.getReview(reviewId,connection);
        if (review != null) {
            System.out.println("\nReview Details:");
            System.out.println("ID: " + review.getReviewId());
            System.out.println("Product ID: " + review.getProductId());
            System.out.println("Customer ID: " + review.getCustomerId());
            System.out.println("Review text: " + review.getReviewText());
            System.out.println("Rating: " + review.getRating());
        } else {
            System.out.println("Review not found.");
        }
    }

    private static void updateReview(Scanner scanner) throws SQLException {
        System.out.print("Enter review ID to update: ");
        int reviewId = scanner.nextInt();
        scanner.nextLine();

        Review review = ReviewDAO.getReview(reviewId,connection);
        if (review != null) {
            System.out.println("Enter new details (press Enter to keep current value):");

            System.out.print("Review text (" + review.getReviewText() + "): ");
            String reviewText = scanner.nextLine();
            if (!reviewText.isEmpty()) {
                review.setReviewText(reviewText);
            }

            System.out.print("Rating (" + review.getRating() + "): ");
            String ratingStr = scanner.nextLine();
            if (!ratingStr.isEmpty()) {
                review.setRating(Integer.parseInt(ratingStr));
            }

            ReviewDAO.updateReview(review,connection);
            System.out.println("Review updated successfully.");
        } else {
            System.out.println("Review not found.");
        }
    }

    private static void deleteReview(Scanner scanner) throws SQLException {
        System.out.print("Enter review ID to delete: ");
        int reviewId = scanner.nextInt();

        Review review = ReviewDAO.getReview(reviewId,connection);
        if (review != null) {
            System.out.print("Are you sure you want to delete this review? (y/n): ");
            String confirm = scanner.next();
            if (confirm.equalsIgnoreCase("y")) {
                ReviewDAO.deleteReview(reviewId,connection);
                System.out.println("Review deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Review not found.");
        }
    }

}
