package bakemyday.storage;

import bakemyday.model.Order;
import bakemyday.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStorage {
    private static final String FILE_PATH = "src/bakemyday/data/orders.txt";

    // ✅ Save order to file
    public static void saveOrder(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(order.toFileFormat());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    // ✅ Load customer orders
    public static List<Order> loadOrders(String customerUsername) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3 && parts[0].equals(customerUsername)) {
                    String address = parts[1];
                    String[] productData = parts[2].split("\\|");

                    List<Product> orderedProducts = new ArrayList<>();
                    for (String productStr : productData) {
                        String[] productParts = productStr.split(",");
                        if (productParts.length == 5) {
                            orderedProducts.add(new Product(
                                    productParts[0],  // Name
                                    Integer.parseInt(productParts[1]), // Quantity
                                    Double.parseDouble(productParts[2]), // Price
                                    productParts[3], // Category
                                    productParts[4], // Description
                                    "N/A" // Seller not needed in history
                            ));
                        }
                    }
                    orders.add(new Order(customerUsername, orderedProducts, address));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }
}
