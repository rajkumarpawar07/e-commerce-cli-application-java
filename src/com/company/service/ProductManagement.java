package com.company.service;
import com.company.model.Product;

import java.sql.*;
import java.util.Scanner;

public class ProductManagement {
    private static Connection connection;

    public ProductManagement(Connection connection) {
        ProductManagement.connection = connection;
    }

    public static void ProductManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println();
            System.out.println("***** Product Management *****");
            System.out.println("1. Add New Product");
            System.out.println("2. View Product Details");
            System.out.println("3. Update Product Information");
            System.out.println("4. Delete Product");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNewProduct(scanner);
                    break;
                case 2:
                    viewProductDetails(scanner);
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static void addNewProduct(Scanner scanner) {
        // get details of new product
        System.out.println("\nEnter product details:");
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Description: ");
        String description = scanner.nextLine();
        int categoryId = 1;
        System.out.println("Category ID: "+categoryId);
        Product product = new Product(0, name, price, description, categoryId);

        // sql query that add new product in database
        String sql = "INSERT INTO Product (product_name, price, description, category_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getDescription());
            pstmt.setInt(4, product.getCategoryId());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getInt(1));
                }
            }
       System.out.println("Product added successfully with ID: " + product.getProductId());
        } catch (SQLException e) {
            System.out.println("Error while adding new product"+e.getMessage());
        }
    }

    public static void viewProductDetails(Scanner scanner)  {
        // get product Id
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        // get product from database
        Product product = null;
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product= new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            rs.getInt("category_id")
                    );
                }
            }
        }catch (SQLException e) {
            System.out.println("Error while adding new product"+e.getMessage());
        }
        // display the product details
        if (product != null) {
            System.out.println("\nProduct Details Found:");
            System.out.println("ID: " + product.getProductId());
            System.out.println("Name: " + product.getProductName());
            System.out.println("Price: $" + product.getPrice());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Category ID: " + product.getCategoryId());
        } else {
            System.out.println("\nProduct with ID "+productId+" does not exist.");
        }
    }

    public static  Product getProduct(int productId){
        Product product = null;
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product= new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            rs.getInt("category_id")
                    );
                }
            }
        }catch (SQLException e) {
            System.out.println("Error while adding new product"+e.getMessage());
        }
        return  product;
    }

    public static void updateProduct(Scanner scanner) {
        // get the product to update by it's id
        System.out.print("Enter product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        // get product from database
        Product product = getProduct(productId);

        // get details for updated product
        if (product != null) {
            System.out.println("Enter new details (press Enter to keep current value):");

            System.out.print("Name (" + product.getProductName() + "): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                product.setProductName(name);
            }

            System.out.print("Price ($" + product.getPrice() + "): ");
            String priceStr = scanner.nextLine();
            if (!priceStr.isEmpty()) {
                product.setPrice(Double.parseDouble(priceStr));
            }

            System.out.print("Description (" + product.getDescription() + "): ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) {
                product.setDescription(description);
            }

            System.out.print("Category ID (" + product.getCategoryId() + "): ");
            String categoryIdStr = scanner.nextLine();
            if (!categoryIdStr.isEmpty()) {
                product.setCategoryId(Integer.parseInt(categoryIdStr));
            }

            //  update Product in database
            String sql = "UPDATE Product SET product_name = ?, price = ?, description = ?, category_id = ? WHERE product_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, product.getProductName());
                pstmt.setDouble(2, product.getPrice());
                pstmt.setString(3, product.getDescription());
                pstmt.setInt(4, product.getCategoryId());
                pstmt.setInt(5, product.getProductId());
                pstmt.executeUpdate();
                System.out.println("Product updated successfully with ID "+productId);
            }catch (SQLException e) {
                System.out.println("Error while updating product: "+e.getMessage());
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void deleteProduct(Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();

        Product product = getProduct(productId);
        if (product != null) {
            System.out.print("Are you sure you want to delete this product? (y/n): ");
            String confirm = scanner.next();
            if (confirm.equalsIgnoreCase("y")) {
                String sql = "DELETE FROM Product WHERE product_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, productId);
                    pstmt.executeUpdate();
                }catch (SQLException e) {
                    System.out.println("Error while deleting product: "+e.getMessage());
                }
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }
}

