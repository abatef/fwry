import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product p1 = new ShippableProduct("TV", 9000, 12, 5000);
        Product p2 = new ShippableProduct("Phone", 5000, 20, 240);
        Product p3 = new ShippableExpirableProduct("Cheese", 50, 1, 1000, LocalDate.now().plusMonths(6));
        Product p4 = new ShippableExpirableProduct("Biscuit", 50, 40, 20, LocalDate.now().minusDays(6));
        Product p5 = new Product("Phone Card", 50, 100);

        Customer customer = new Customer("John");
        customer.setBalance(20000);

        CartService cartService = CartService.getInstance();
        cartService.createCart(customer);

        cartService.addProduct(customer, p1, 1); // Ok
        cartService.checkout(customer);
        cartService.addProduct(customer, p2, 1); // Ok
        cartService.checkout(customer);
        cartService.addProduct(customer, p3, 2); // Error: Insufficient Quantity
        cartService.checkout(customer);
        cartService.addProduct(customer, p4, 3); // Error: Expired Product
        cartService.checkout(customer);
        cartService.addProduct(customer, p5, 4); // Ok Non-Shippable Non-Expirable Product So Shipping Fails
        cartService.checkout(customer);
        cartService.addProduct(customer, p1, 1); // Error: Insufficient Balance
        cartService.checkout(customer);
    }
}