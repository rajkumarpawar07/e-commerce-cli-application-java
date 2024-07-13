package com.company.dao;

import com.company.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingDAO {

    public static double calculateAverageRating(int productId, Connection connection) throws SQLException {
        String sql = "SELECT AVG(rating) as avg_rating FROM Review WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        }
        return 0;
    }


    public static List<Product> getTopRatedProducts(int limit, Connection connection) throws SQLException {
        String sql = "SELECT p.*, AVG(r.rating) as avg_rating " +
                "FROM Product p " +
                "JOIN Review r ON p.product_id = r.product_id " +
                "GROUP BY p.product_id " +
                "ORDER BY avg_rating DESC " +
                "LIMIT ?";
        List<Product> topRatedProducts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            rs.getInt("category_id")
                    );
                    topRatedProducts.add(product);
                }
            }
        }
        return topRatedProducts;
    }

    public static Map<Integer, Integer> getRatingsDistribution(int productId, Connection connection) throws SQLException {
        Map<Integer, Integer> distribution = new HashMap<>();

        // Initialize the distribution map with zero counts for all ratings
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }

        String sql = "SELECT rating, COUNT(*) as count FROM Review WHERE product_id = ? GROUP BY rating";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int rating = rs.getInt("rating");
                    int count = rs.getInt("count");
                    distribution.put(rating, count);
                }
            }
        }

        return distribution;
    }

}
