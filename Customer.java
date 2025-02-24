package bakemyday.model;

public class Customer extends User {
    public Customer(String username, String email, String phone, String password) {
        super(username, email, phone, password, "Customer");
    }
    @Override
    public void displayInfo() {
        System.out.println("Customer Profile");
        super.displayInfo();
    }
}
