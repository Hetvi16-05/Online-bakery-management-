package bakemyday.menus;

import bakemyday.model.controller.ProductController;

// ✅ Seller menu extending UserMenu
public class SellerMenu extends UserMenu {
    private final ProductController productController = new ProductController();

    @Override
    public void displayMenu(String sellerUsername) {
        while (true) {
            System.out.println("\n===== Seller Product & Delivery Management =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View My Products");
            System.out.println("5. Schedule Delivery");
            System.out.println("6. Update Delivery Status (Mark as Delivered)");
            System.out.println("7. View All Deliveries (Active & Past)");
            System.out.println("8. Logout");
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
                    productController.addProduct(sellerUsername);
                    break;
                case 2 :
                    productController.updateProduct(sellerUsername);
                    break;
                case 3 :
                    productController.deleteProduct(sellerUsername);
                    break;
                case 4 :
                    productController.viewSellerProducts(sellerUsername);
                    break;
                case 5 :
                    productController.scheduleDelivery(sellerUsername);
                    break;
                case 6 :
                    productController.updateDeliveryStatus(sellerUsername);
                    break;
                case 7 :
                    productController.viewAllDeliveries(sellerUsername);
                    break;
                case 8 :
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
        
    }
}
