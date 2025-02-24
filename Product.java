package bakemyday.model;
public class Product {
    private final String name;
    private int quantity; 
    private double price;
    private final String category;
    private final String description;
    private final String sellerUsername;
    // ✅ Constructor
    public Product(String name, int quantity, double price, String category, String description, String sellerUsername) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.description = description;
        this.sellerUsername = sellerUsername;
    }
    // ✅ Getters
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getSellerUsername() { return sellerUsername; }    
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPricePerKg(double price) { this.price = price; }
    public void displayProduct() {
        System.out.println("\n Product Name: " + name);
        System.out.print("\tQuantity: " + quantity );
        System.out.print("\tPrice: Rs. " + price );
        System.out.print("\tCategory: " + category);
        System.out.print("\tDescription: " + description);
        System.out.print("\tSeller: " + sellerUsername);
    }
    public String toFileFormat() {
        return name + "," + quantity + "," + price + "," + category + "," + description + "," + sellerUsername;
    }

}
