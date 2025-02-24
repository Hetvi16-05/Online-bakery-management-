package bakemyday;
import bakemyday.exceptions.CustomExceptions;
import bakemyday.management.AuthenticationManager;
import bakemyday.menus.CustomerMenu;
import bakemyday.menus.SellerMenu;
import bakemyday.model.User;
import java.util.Scanner;
public class BakeMyDay {
    private final AuthenticationManager authManager = new AuthenticationManager();
    private final Scanner scanner = new Scanner(System.in);
    
    public void start() {
        while (true) {
            System.out.println("\n1. Signup\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ✅ Signup Method
    private void signUp() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!User.isValidEmail(email)) {
            System.out.println("Invalid email format! Try again.");
            return;
        }

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        if (!User.isValidPhone(phone)) {
            System.out.println("Invalid phone number! Must be exactly 10 digits.");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Role (Customer/Seller): ");
        String role = scanner.nextLine().trim().toLowerCase();
        if (!role.equals("customer") && !role.equals("seller")) {
            System.out.println("Invalid role! Enter 'Customer' or 'Seller'.");
            return;
        }
        try {
    authManager.signUp(username, email, phone, password, role);
    } catch (CustomExceptions.InvalidUserException e) {
    System.out.println(e.getMessage());
    }
    }
    // ✅ Login Method
    private void login() {
    System.out.print("Enter Username: ");
    String username = scanner.nextLine();

    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    try {
        User user = authManager.login(username, password);
        
        if (user == null) {
            System.out.println("Login failed! Invalid credentials.");
            return;
        }

        System.out.println("Welcome, " + user.getRole() + " " + user.getUsername() + "!");
        user.displayInfo();

        if (user.getRole().equalsIgnoreCase("seller")) {
            new SellerMenu().displayMenu(user.getUsername());
        } else {
            new CustomerMenu().displayMenu(user.getUsername());
        }

    } catch (bakemyday.exceptions.CustomExceptions.InvalidUserException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("An unexpected error occurred. Please try again.");
    }
}
}
