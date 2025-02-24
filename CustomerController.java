package bakemyday.model.controller;
import bakemyday.exceptions.CustomExceptions;
import bakemyday.management.OrderManager;
import bakemyday.management.ProductManager;
import bakemyday.model.Order;
import bakemyday.model.Product;
import bakemyday.storage.CartStorage;
import java.util.*;

public class CustomerController {
    private final Map<String, String> customerAddresses = new HashMap<>(); // Stores customer addresses
    private final Map<String, List<Product>> customerCarts = new HashMap<>(); // Stores carts per customer
    private final OrderManager orderManager = new OrderManager();
    private final ProductManager productManager = new ProductManager();
    private final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> allowedAreas = new HashMap<>();
    static {allowedAreas.put("alkapuri", "390007");
    allowedAreas.put("manjalpur", "390011");
    allowedAreas.put("gotri", "390021");
    allowedAreas.put("karelibaug", "390018");
    allowedAreas.put("fatehgunj", "390002");
    allowedAreas.put("subhanpura", "390023");
    allowedAreas.put("vasna", "390015");
    allowedAreas.put("bhayli", "391410");
    }
    private List<Product> getCart(String customerUsername) {
    return CartStorage.loadCart(customerUsername);
}

    // ✅ View All Products
    public void viewProducts() {
        List<Product> products = productManager.loadProducts();
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }
        System.out.println("\n===== Available Products =====");
        for (Product product : products) {
            product.displayProduct();
        }
    }
    
    public void viewProductsByCategory() {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        List<Product> products = productManager.loadProducts();
        boolean found = false;
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                product.displayProduct();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found in this category.");
        }
    }
    public void addToCart(String customerUsername) {
    try {
        System.out.print("Enter Product Name to Add: ");
        String productName = scanner.nextLine();

        List<Product> products = productManager.loadProducts();
        List<Product> cart = getCart(customerUsername);  // ✅ Load cart

        Product selectedProduct = null;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct == null) {
            throw new CustomExceptions.ProductNotFoundException("❌ Product not found: " + productName);
        }

        System.out.print("Enter Quantity: ");
        if (!scanner.hasNextInt()) {
            System.out.println("❌ Invalid input. Please enter a whole number.");
            scanner.nextLine();
            return;
        }
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (quantity <= 0) {
            System.out.println("❌ Quantity must be greater than zero.");
            return;
        }

        if (selectedProduct.getQuantity() < quantity) {
            System.out.println("❌ Stock not available. Only " + selectedProduct.getQuantity() + " left.");
            return;
        }

        // ✅ Check if the product already exists in the cart
        boolean productExists = false;
        for (Product cartProduct : cart) {
            if (cartProduct.getName().equalsIgnoreCase(selectedProduct.getName())) {
                cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            Product cartProduct = new Product(
                selectedProduct.getName(), quantity, selectedProduct.getPrice(),
                selectedProduct.getCategory(), selectedProduct.getDescription(), selectedProduct.getSellerUsername()
            );
            cart.add(cartProduct);
        }

        // ✅ Save cart but do NOT update product stock yet
        CartStorage.saveCart(customerUsername, cart);

        System.out.println("✅ " + quantity + " KG of " + selectedProduct.getName() + " added to cart!");

    } catch (CustomExceptions.ProductNotFoundException e) {
        System.out.println(e.getMessage());
    } catch (InputMismatchException e) {
        System.out.println("❌ Invalid input. Please enter a valid quantity.");
        scanner.nextLine();
    }
}


    public void viewCart(String customerUsername) {
        List<Product> cartItems = getCart(customerUsername);
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }        
        System.out.println("\n===== Your Cart =====");
        double totalPrice = 0;
        for (Product product : cartItems) {
            product.displayProduct();
            totalPrice += product.getPrice() * product.getQuantity();
        }
        System.out.println("\nTotal Price: ₹" + totalPrice);
    }

    public void updateCartProduct(String customerUsername) {
    List<Product> cartItems = getCart(customerUsername);
    if (cartItems.isEmpty()) {
        System.out.println("Your cart is empty! Cannot update.");
        return;
    }

    System.out.print("Enter Product Name to Update: ");
    String productName = scanner.nextLine();
    boolean found = false;

    for (Product product : cartItems) {
        if (product.getName().equalsIgnoreCase(productName)) {
            System.out.print("Enter New Quantity: ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();

            if (newQuantity <= 0) {
                System.out.println("❌ Quantity must be greater than zero.");
                return;
            }

            product.setQuantity(newQuantity);
            CartStorage.saveCart(customerUsername, cartItems);  // ✅ Save changes
            System.out.println("✅ Product updated successfully!");
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("❌ Product not found in your cart.");
    }
}


    public void deleteProductFromCart(String customerUsername) {
    List<Product> cartItems = getCart(customerUsername);
    if (cartItems.isEmpty()) {
        System.out.println("Your cart is empty! Cannot delete.");
        return;
    }

    System.out.print("Enter Product Name to Remove: ");
    String productName = scanner.nextLine();
    boolean removed = cartItems.removeIf(product -> product.getName().equalsIgnoreCase(productName));

    if (removed) {
        CartStorage.saveCart(customerUsername, cartItems);  // ✅ Save changes
        System.out.println("✅ Product removed from cart!");
    } else {
        System.out.println("❌ Product not found in your cart.");
    }
}

    public void placeOrder(String customerUsername) {
    List<Product> cartItems = getCart(customerUsername);
    if (cartItems.isEmpty()) {
        System.out.println("❌ Your cart is empty!");
        return;
    }

    // ✅ Load all products
    List<Product> products = productManager.loadProducts();

    // ✅ Check stock before placing the order
    for (Product cartProduct : cartItems) {
        boolean productExists = false;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(cartProduct.getName())) {
                if (product.getQuantity() < cartProduct.getQuantity()) {
                    System.out.println("❌ Stock not available for " + product.getName() +
                            ". Only " + product.getQuantity() + " left.");
                    return;
                }
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            System.out.println("❌ Product " + cartProduct.getName() + " not found in stock.");
            return;
        }
    }

    // ✅ Reduce stock only now
    for (Product cartProduct : cartItems) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(cartProduct.getName())) {
                product.setQuantity(product.getQuantity() - cartProduct.getQuantity()); // Reduce stock
                break;
            }
        }
    }

    // ✅ Save updated stock
    productManager.saveProducts(products);

    // ✅ Process and save the order
    Order order = new Order(customerUsername, cartItems, customerAddresses.get(customerUsername));
    order.displayOrder();
    orderManager.placeOrder(order);

    // ✅ Clear cart after order placement
    CartStorage.clearCart(customerUsername);
    System.out.println("✅ Order placed successfully!");
}

    public void viewOrderHistory(String customerUsername) {
        List<Order> orders = orderManager.getOrderHistory(customerUsername);
        if (orders.isEmpty()) {
            System.out.println("No past orders found.");
            return;
        }
        for (Order order : orders) {
            order.displayOrder();
        }
    }   
}
