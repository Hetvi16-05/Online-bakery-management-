package bakemyday.management;
import bakemyday.exceptions.CustomExceptions.*;
import bakemyday.model.Product;
import bakemyday.storage.ProductStorage;
import java.util.List;

public class ProductManager {
    public List<Product> loadProducts() {
        return ProductStorage.loadProducts();
    }
    // ✅ Add Product
    public void addProduct(Product product) {
    if (product.getQuantity() < 0) {
        System.out.println("❌ Cannot add product with negative stock.");
        return;
    }
    ProductStorage.addProduct(product);
    System.out.println("✅ Product added successfully!");
}


    // ✅ Update Product with Exception Handling
    public void updateProduct(String productName, String sellerUsername, int newQuantity, double newPrice) 
            throws ProductNotFoundException {
        boolean updated = ProductStorage.updateProduct(productName, sellerUsername, newQuantity, newPrice);
        if (!updated) {
            throw new ProductNotFoundException("Product not found or you do not have permission to update it.");
        }
        System.out.println("Product updated successfully!");
    }

    // ✅ Delete Product with Exception Handling
    public void deleteProduct(String productName, String sellerUsername) throws ProductNotFoundException {
        boolean deleted = ProductStorage.deleteProduct(productName, sellerUsername);
        if (!deleted) {
            throw new ProductNotFoundException("Product not found or you do not have permission to delete it.");
        }
        System.out.println("Product deleted successfully!");
    }

    // ✅ View All Products
    public void viewAllProducts() {
        List<Product> products = loadProducts();
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }
        System.out.println("\n===== Available Products =====");
        for (Product product : products) {
            product.displayProduct();
        }
    }
    // ✅ View Products by Category
    public void viewProductsByCategory(String category) {
        List<Product> products = loadProducts();
        boolean found = false;

        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                product.displayProduct();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found in this category.");
        }
    }
    // ✅ Save Updated Product List to Storage
    public void saveProducts(List<Product> products) {
    ProductStorage.saveProducts(products);
    }

    // ✅ View Seller's Products
    public void viewSellerProducts(String sellerUsername) {
        List<Product> products = loadProducts();
        boolean found = false;

        System.out.println("\n===== Products Sold by " + sellerUsername + " =====");
        for (Product product : products) {
            if (product.getSellerUsername().equalsIgnoreCase(sellerUsername)) {
                product.displayProduct();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found for this seller.");
        }
    }
}
