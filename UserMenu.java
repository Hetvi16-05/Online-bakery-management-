package bakemyday.menus;

import bakemyday.management.AuthenticationManager;
import java.util.Scanner;

// ✅ Base class for common functionality
public abstract class UserMenu {
    protected final AuthenticationManager authManager = new AuthenticationManager();
    protected final Scanner scanner = new Scanner(System.in);
    public abstract void displayMenu(String username);
}
