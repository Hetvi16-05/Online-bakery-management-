package bakemyday.management;

import bakemyday.interfaces.DeliveryOperations;
import java.util.*;

public class DeliveryManager implements DeliveryOperations {
    private final Map<String, String> activeDeliveries = new HashMap<>(); // Stores active deliveries
    private final Map<String, String> pastDeliveries = new HashMap<>();   // Stores completed deliveries
    private final Scanner scanner = new Scanner(System.in);
    private final OrderManager orderManager = new OrderManager(); // ✅ Use OrderManager to check valid orders

    // ✅ Schedule a New Delivery
    @Override
    public void scheduleDelivery(String sellerUsername) {
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();

        // ✅ Check if Order ID is valid
        if (!orderManager.isValidOrder(orderId)) {
            System.out.println("❌ Invalid Order ID! Please enter a valid order.");
            return;
        }

        System.out.print("Enter Delivery Address: ");
        String address = scanner.nextLine();

        activeDeliveries.put(orderId, "Scheduled - " + address);
        System.out.println("✅ Delivery scheduled successfully for Order ID: " + orderId);
    }

    @Override
    public void updateDeliveryStatus(String sellerUsername) {
        try {
            List<String> lines = new ArrayList<>();
            boolean orderFound = false;

            System.out.print("Enter Order ID to update: ");
            String orderId = scanner.nextLine();

            // ✅ Validate Order ID before updating
            if (!orderManager.isValidOrder(orderId)) {
                System.out.println("❌ Invalid Order ID! Please enter a valid order.");
                return;
            }

            System.out.print("Enter New Status (Out for Delivery / Delivered): ");
            String newStatus = scanner.nextLine().trim();

            if (!newStatus.equalsIgnoreCase("Out for Delivery") && !newStatus.equalsIgnoreCase("Delivered")) {
                System.out.println("❌ Invalid status! Please enter either 'Out for Delivery' or 'Delivered'.");
                return;
            }

            // ✅ Prevent skipping 'Out for Delivery' before 'Delivered'
            if (newStatus.equalsIgnoreCase("Delivered") && !activeDeliveries.containsKey(orderId)) {
                System.out.println("❌ Order must first be 'Out for Delivery' before it can be marked 'Delivered'.");
                return;
            }

            // ✅ Update delivery status
            activeDeliveries.put(orderId, newStatus);
            if (newStatus.equalsIgnoreCase("Delivered")) {
                pastDeliveries.put(orderId, "Delivered");
                activeDeliveries.remove(orderId);
            }

            System.out.println("✅ Order ID " + orderId + " updated to " + newStatus);

        } catch (Exception e) {
            System.out.println("Unexpected error. Please try again.");
        }
    }

    // ✅ View All Active & Past Deliveries
    @Override
    public void viewAllDeliveries(String sellerUsername) {
        System.out.println("\n===== Active Deliveries =====");
        boolean hasActive = false;

        for (Map.Entry<String, String> entry : activeDeliveries.entrySet()) {
            if (orderManager.isValidOrder(entry.getKey())) {  // ✅ Only show valid orders
                System.out.println("Order ID: " + entry.getKey() + " | Status: " + entry.getValue());
                hasActive = true;
            }
        }
        if (!hasActive) {
            System.out.println("No active deliveries.");
        }

        System.out.println("\n===== Past Deliveries =====");
        if (pastDeliveries.isEmpty()) {
            System.out.println("No past deliveries.");
        } else {
            for (Map.Entry<String, String> entry : pastDeliveries.entrySet()) {
                System.out.println("Order ID: " + entry.getKey() + " | Status: " + entry.getValue());
            }
        }
    }
}
