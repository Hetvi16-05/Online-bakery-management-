package bakemyday.model.controller;

import bakemyday.exceptions.CustomExceptions.ProductNotFoundException;
import bakemyday.management.DeliveryManager;
import bakemyday.management.ProductManager;
import bakemyday.model.Product;
import java.util.Scanner;

public class ProductController {
    private final ProductManager productManager = new ProductManager();
    private final DeliveryManager deliveryManager = new DeliveryManager();
    private final Scanner scanner = new Scanner(System.in);

    // ✅ Add Product (For Sellers)
    public void addProduct(String sellerUsername) {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Quantity : ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Price : ");
        double pricePerKg = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        Product product = new Product(name, quantity, pricePerKg, category, description, sellerUsername);
        productManager.addProduct(product);
        System.out.println("Product added successfully!");
    }
    // ✅ Update Product (Only Seller Can Update)
    public void updateProduct(String sellerUsername) {
        System.out.print("Enter Product Name to Update: ");
        String productName = scanner.nextLine();
        System.out.print("Enter New Quantity : ");
        int newQuantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter New Price : ");
        double newPrice = Double.parseDouble(scanner.nextLine());

        try {
            productManager.updateProduct(productName, sellerUsername, newQuantity, newPrice);
            System.out.println("Product updated successfully!");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    // ✅ Delete Product (Only Seller Can Delete)
    public void deleteProduct(String sellerUsername) {
        System.out.print("Enter Product Name to Delete: ");
        String productName = scanner.nextLine();

        try {
            productManager.deleteProduct(productName, sellerUsername);
            System.out.println("Product deleted successfully!");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    // ✅ View All Products
    public void viewAllProducts() {
        productManager.viewAllProducts();
    }
    // ✅ View Products by Category
    public void viewProductsByCategory() {
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        productManager.viewProductsByCategory(category);
    }
    // ✅ View Seller's Products Only
    public void viewSellerProducts(String sellerUsername) {
        productManager.viewSellerProducts(sellerUsername);
    }
    // ✅ Schedule Delivery (For Sellers)
    public void scheduleDelivery(String sellerUsername) {
        deliveryManager.scheduleDelivery(sellerUsername);
    }
    // ✅ Update Delivery Status (For Sellers)
    public void updateDeliveryStatus(String sellerUsername) {
        deliveryManager.updateDeliveryStatus(sellerUsername);
    }
    // ✅ View All Active & Past Deliveries (For Sellers)
    public void viewAllDeliveries(String sellerUsername) {
        deliveryManager.viewAllDeliveries(sellerUsername);
    }
}
