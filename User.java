package bakemyday.model;

import java.util.regex.Pattern;

public class User {
    protected String username;
    protected String email;
    protected String phone;
    protected String password;
    protected String role; // Either "Customer" or "Seller"

    // Constructor
    public User(String username, String email, String phone, String password, String role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // ✅ Email Validation
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-z0-9+_.-]+@[a-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // ✅ Phone Number Validation
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}"); // Must be exactly 10 digits
    }

    // ✅ Display user info (Can be overridden)
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Role: " + role);
    }
}
