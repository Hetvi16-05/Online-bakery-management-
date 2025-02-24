package bakemyday.storage;

import bakemyday.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductStorage {
    private static final String FILE_PATH = "src/bakemyday/data/products.txt";

    // ✅ Load Products from File
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) return products; // If file doesn't exist, return empty list

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    products.add(new Product(
                        parts[0], // Name
                        Integer.parseInt(parts[1]), // Quantity
                        Double.parseDouble(parts[2]), // Price per KG
                        parts[3], // Category
                        parts[4], // Description
                        parts[5]  // Seller Username
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
        return products;
    }

    // ✅ Save Products to File
    public static void saveProducts(List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                bw.write(product.toFileFormat());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving products: " + e.getMessage());
        }
    }

    // ✅ Add Product to Storage
    public static void addProduct(Product product) {
        List<Product> products = loadProducts();
        products.add(product);
        saveProducts(products);
    }

    // ✅ Update Product in Storage
    public static boolean updateProduct(String productName, String sellerUsername, int newQuantity, double newPrice) {
        List<Product> products = loadProducts();
        boolean found = false;

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName) && product.getSellerUsername().equals(sellerUsername)) {
                product.setQuantity(newQuantity);
                product.setPricePerKg(newPrice);
                found = true;
                break;
            }
        }

        if (found) {
            saveProducts(products);
        }
        return found;
    }
    // ✅ Delete Product from Storage
    public static boolean deleteProduct(String productName, String sellerUsername) {
        List<Product> products = loadProducts();
        boolean removed = products.removeIf(product ->
            product.getName().equalsIgnoreCase(productName) && product.getSellerUsername().equals(sellerUsername));

        if (removed) {
            saveProducts(products);
        }
        return removed;
    }
}