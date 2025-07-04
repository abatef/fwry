import java.util.HashMap;
import java.util.Map;

public class CartService {
    private final static CartService cartService = new CartService();
    private final Map<Customer, Cart> carts = new HashMap<>();

    private CartService() {
    }

    public static CartService getInstance() {
        return cartService;
    }

    public void checkout(Customer customer) {
        try {
            Cart cart = carts.get(customer);
            double total = cart.checkout();
            if (total <= customer.getBalance()) {
                customer.setBalance(customer.getBalance() - total);
                System.out.println("------------------");
                System.out.println( AnsiColors.ANSI_GREEN +  "Checkout Successful");
                System.out.printf("Current Balance: %6.2f\n" + AnsiColors.ANSI_RESET, customer.getBalance());
                System.out.println("------------------");
                cart.completeCheckout(customer);

            } else {
                throw new RuntimeException("\nInsufficient Balance: " + customer.getBalance() +
                        "\nRequired: " + total);
            }
        } catch (Exception e) {
            String ANSI_RED = "\u001B[31m";
            String ANSI_RESET = "\u001B[0m";
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
            System.out.println("------------------");
        }
    }

    public void addProduct(Customer customer, Product product, int quantity) {
        Cart cart = carts.get(customer);
        try {
            cart.add(product, quantity);
        } catch (Exception e) {
            String ANSI_RED = "\u001B[31m";
            String ANSI_RESET = "\u001B[0m";
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        }
    }

    public void createCart(Customer customer) {
        carts.put(customer, new Cart());
    }

    public void clearCart(Customer customer) {
        carts.get(customer).clear();
    }
}
