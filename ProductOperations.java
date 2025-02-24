package bakemyday.interfaces;

import bakemyday.model.Product;
import java.util.List;

public interface ProductOperations {
    void addProduct(Product product);
    void updateProduct(String productName, String sellerUsername, double newQuantity, double newPrice);
    void deleteProduct(String productName, String sellerUsername);
    List<Product> loadProducts();
    void saveProducts(List<Product> products);
    void viewSellerProducts(String sellerUsername);
}
