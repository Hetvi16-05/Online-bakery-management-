package bakemyday.storage;

import bakemyday.model.Customer;
import bakemyday.model.Seller;
import bakemyday.model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static final String FILE_PATH = "src/bakemyday/data/user.txt";

    // ✅ Load users from file
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Check for all fields: username, email, phone, password, role
                    String username = parts[0];
                    String email = parts[1];
                    String phone = parts[2];
                    String password = parts[3];
                    String role = parts[4];

                    if (role.equalsIgnoreCase("customer")) {
                        users.add(new Customer(username, email, phone, password));
                    } else {
                        users.add(new Seller(username, email, phone, password));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // ✅ Save users to file
    public void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                bw.write(user.getUsername() + "," + user.getEmail() + "," + user.getPhone() + "," + user.getPassword() + "," + user.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}
