package bakemyday.exceptions;

public class CustomExceptions {
    
    // Invalid User Exception (Wrong Username or Password)
    public static class InvalidUserException extends Exception {
        public InvalidUserException(String message) {
            super(message);
        }
    }

    // Product Not Found Exception
    public static class ProductNotFoundException extends Exception {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    // Out of Stock Exception
    public static class OutOfStockException extends Exception {
        public OutOfStockException(String message) {
            super(message);
        }
    }

    
    // Invalid Delivery Exception
    public static class InvalidDeliveryException extends Exception {
        public InvalidDeliveryException(String message) {
            super(message);
        }
    }
}
