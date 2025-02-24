package bakemyday.model;

public class Seller extends User {
    public Seller(String username, String email, String phone, String password) {
        super(username, email, phone, password, "Seller");
    }

    @Override
    public void displayInfo() {
        System.out.println("Seller Profile");
        super.displayInfo();
    }
}
