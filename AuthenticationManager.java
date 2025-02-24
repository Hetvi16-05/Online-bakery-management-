package bakemyday.management;

import bakemyday.model.Customer;
import bakemyday.exceptions.CustomExceptions.InvalidUserException;
import bakemyday.model.Seller;
import bakemyday.model.User;
import bakemyday.storage.UserStorage;
import java.util.List;
import java.util.Optional;

public class AuthenticationManager {
    private final UserStorage userStorage;

    public AuthenticationManager() {
        this.userStorage = new UserStorage();
    }

    // ✅ Signup method
    public boolean signUp(String username, String email, String phone, String password, String role) throws InvalidUserException {
        List<User> users = userStorage.loadUsers();

        // ✅ Validate Email & Phone
        if (!User.isValidEmail(email)) {
            throw new InvalidUserException("Invalid email format! Try again.");
        }
        if (!User.isValidPhone(phone)) {
            throw new InvalidUserException("Invalid phone number! Must be exactly 10 digits.");
        }

        // ✅ Check if username or email already exists
        Optional<User> existingUser = users.stream()
            .filter(user -> user.getUsername().equals(username) || user.getEmail().equals(email))
            .findFirst();

        if (existingUser.isPresent()) {
            throw new InvalidUserException("Username or Email already exists. Choose another.");
        }

        // ✅ Validate Role
        User newUser;
        if ("customer".equalsIgnoreCase(role)) {
            newUser = new Customer(username, email, phone, password);
        } else if ("seller".equalsIgnoreCase(role)) {
            newUser = new Seller(username, email, phone, password);
        } else {
            throw new InvalidUserException("Invalid role! Please choose 'customer' or 'seller'.");
        }

        // ✅ Save User
        users.add(newUser);
        userStorage.saveUsers(users);
        return true;
    }

    // ✅ Login method
    public User login(String username, String password) throws InvalidUserException {
    if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
        throw new InvalidUserException("Username or password cannot be empty.");
    }

    // Loop through users and find the match
    for (User user : userStorage.loadUsers()) {
        if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
            return user;
        }
    }

    // If no matching user is found
    throw new InvalidUserException("Invalid username or password.");
}


}
