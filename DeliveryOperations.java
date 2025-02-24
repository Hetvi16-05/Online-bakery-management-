package bakemyday.interfaces;

public interface DeliveryOperations {
    void scheduleDelivery(String sellerUsername);
    void updateDeliveryStatus(String sellerUsername);
    void viewAllDeliveries(String sellerUsername);
}
