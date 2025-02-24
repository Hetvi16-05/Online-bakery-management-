package bakemyday.model;

import java.util.List;

public class Order extends BaseOrder {
    private String status; // Added status field

    public Order(String customerUsername, List<Product> orderedProducts, String address) {
        super(customerUsername, orderedProducts, address);
        this.status = "Pending"; // Default status when order is placed
    }

    // New constructor that supports order status
    public Order(String customerUsername, List<Product> orderedProducts, String address, String status) {
        super(customerUsername, orderedProducts, address);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void displayOrder() {
        System.out.println("\n===== Order Summary =====");
        System.out.println("Customer: " + customerUsername);
        System.out.println("Delivery Address: " + address);
        System.out.println("Status: " + status); // Display order status
        for (Product product : orderedProducts) {
            product.displayProduct();
        }
    }

    public String toFileFormat() {
        StringBuilder productData = new StringBuilder();
        for (Product product : orderedProducts) {
            productData.append(product.getName()).append(",")
                    .append(product.getQuantity()).append(",")
                    .append(product.getPrice()).append(",")
                    .append(product.getCategory()).append(",")
                    .append(product.getDescription()).append("|");
        }
        return customerUsername + ";" + address + ";" + productData + ";" + status;
    }
}
