package com.company.dao;
import com.company.model.Review;
import java.sql.*;

public class ReviewDAO {

    // add review
    public static void addReview(Review review, Connection connection) throws SQLException {
        String sql = "INSERT INTO Review (product_id, customer_id, review_text, rating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, review.getProductId());
            pstmt.setInt(2, review.getCustomerId());
            pstmt.setString(3, review.getReviewText());
            pstmt.setInt(4, review.getRating());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.setReviewId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // View review details
    public static Review getReview(int reviewId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM Review WHERE review_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Review(
                            rs.getInt("review_id"),
                            rs.getInt("product_id"),
                            rs.getInt("customer_id"),
                            rs.getString("review_text"),
                            rs.getInt("rating")
                    );
                }
            }
        }
        return null;
    }

    public static void updateReview(Review review, Connection connection) throws SQLException {
        String sql = "UPDATE Review SET product_id = ?, customer_id = ?, review_text = ?, rating = ? WHERE review_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, review.getProductId());
            pstmt.setInt(2, review.getCustomerId());
            pstmt.setString(3, review.getReviewText());
            pstmt.setInt(4, review.getRating());
            pstmt.setInt(5, review.getReviewId());
            pstmt.executeUpdate();
        }
    }

    public static void deleteReview(int reviewId, Connection connection) throws SQLException {
        String sql = "DELETE FROM Review WHERE review_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        }
    }
}
