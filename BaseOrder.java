package bakemyday.model;

import java.util.List;

public abstract class BaseOrder {
    protected String customerUsername;
    protected List<Product> orderedProducts;
    protected String address;

    public BaseOrder(String customerUsername, List<Product> orderedProducts, String address) {
        this.customerUsername = customerUsername;
        this.orderedProducts = orderedProducts;
        this.address = address;
    }
    public abstract void displayOrder(); // Abstract method for child classes
}
