package bakemyday.interfaces;

import bakemyday.model.Order;
import java.util.List;

public interface OrderOperations {
    void placeOrder(Order order);
    List<Order> getOrderHistory(String customerUsername);
}
