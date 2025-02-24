package bakemyday.management;

import bakemyday.interfaces.OrderOperations;
import bakemyday.model.Order;
import bakemyday.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager implements OrderOperations {
    private static final String FILE_PATH = "src/bakemyday/data/orders.txt";

    @Override
    public void placeOrder(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(order.toFileFormat() + ";Pending"); // New order starts as 'Pending'
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getOrderHistory(String customerUsername) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4 && parts[0].equals(customerUsername)) { // Includes status
                    String address = parts[1];
                    String[] productData = parts[2].split("\\|");
                    String status = parts[3];

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
                                    "N/A"
                            ));
                        }
                    }
                    orders.add(new Order(customerUsername, orderedProducts, address, status));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }

    // ✅ Validate if Order ID exists
    public boolean isValidOrder(String orderId) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";"); // Assuming order format: OrderID;Address;ProductData;Status
                if (parts.length > 0 && parts[0].equals(orderId)) {
                    return true; // ✅ Order ID found
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders: " + e.getMessage());
        }
        return false; // ❌ Order ID not found
    }
}
