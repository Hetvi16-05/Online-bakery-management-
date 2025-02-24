package bakemyday.storage;

import bakemyday.model.Product;
import java.io.*;
import java.util.*;

public class CartStorage {
    private static final String FILE_PATH = "src/bakemyday/data/cart.txt";

    // ✅ Save customer's cart to file
    public static void saveCart(String customerUsername, List<Product> cart) {
        List<String> cartData = new ArrayList<>();
        for (Product product : cart) {
            cartData.add(customerUsername + "," + product.getName() + "," + product.getQuantity() + "," +
                         product.getPrice() + "," + product.getCategory() + "," + product.getDescription() + "," +
                         product.getSellerUsername());
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            for (String line : cartData) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving cart: " + e.getMessage());
        }
    }

    // ✅ Load a customer's cart from file
    public static List<Product> loadCart(String customerUsername) {
        List<Product> cart = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7 && parts[0].equals(customerUsername)) {
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    String category = parts[4];
                    String description = parts[5];
                    String sellerUsername = parts[6];

                    cart.add(new Product(name, quantity, price, category, description, sellerUsername));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading cart: " + e.getMessage());
        }
        return cart;
    }

    // ✅ Remove an item from the cart
    public static void removeFromCart(String customerUsername, String productName) {
        List<String> updatedCart = new ArrayList<>();
        boolean removed = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7 && parts[0].equals(customerUsername) && parts[1].equalsIgnoreCase(productName)) {
                    removed = true; // ✅ Skip this item (removes from cart)
                } else {
                    updatedCart.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading cart file: " + e.getMessage());
            return;
        }

        // ✅ Write the updated cart back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : updatedCart) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error updating cart file: " + e.getMessage());
        }

        if (removed) {
            System.out.println("✅ Product removed from cart!");
        } else {
            System.out.println("❌ Product not found in cart.");
        }
    }

    // ✅ Clear the entire cart for a user (used when placing an order)
    public static void clearCart(String customerUsername) {
        List<String> updatedCart = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!(parts.length == 7 && parts[0].equals(customerUsername))) {
                    updatedCart.add(line); // ✅ Keep other users' cart data
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading cart file: " + e.getMessage());
            return;
        }

        // ✅ Write updated cart without the cleared user's cart
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : updatedCart) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error clearing cart: " + e.getMessage());
        }

        System.out.println("✅ Cart cleared for user: " + customerUsername);
    }
}
