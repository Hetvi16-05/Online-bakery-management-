package bakemyday.menus;
import bakemyday.model.controller.CustomerController;

// ✅ Customer menu extending UserMenu
public class CustomerMenu extends UserMenu {
    private final CustomerController customerController = new CustomerController();
    @Override
    public void displayMenu(String customerUsername) {
        while (true) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View Products");
            System.out.println("2. View Products by Category");
            System.out.println("3. Add to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Place Order");
            System.out.println("6. View Order History");
            System.out.println("7. Update the cart product");
            System.out.println("8. Delete the cart product");
            System.out.println("9. Logout");
            System.out.print("Select an option: ");

            int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            continue;
        }
        switch (choice) {
            case 1 :
                customerController.viewProducts();
                break;
            case 2 :
                customerController.viewProductsByCategory();
                break;
            case 3 :
                customerController.addToCart(customerUsername);
                break;
            case 4 :
                customerController.viewCart(customerUsername);
                break;
            case 5 :
                customerController.placeOrder(customerUsername);
                break;
            case 6 :
                customerController.viewOrderHistory(customerUsername);
                break;
            case 7 :
                customerController.updateCartProduct(customerUsername);
                break;
            case 8 :
                customerController.deleteProductFromCart(customerUsername);
                break;
            case 9:
                System.out.println("Logging out...");
                return;
            default:
                System.out.println("Invalid option! Try again.");
        }
        }
    }
}
